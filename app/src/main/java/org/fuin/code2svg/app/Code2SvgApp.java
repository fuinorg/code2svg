/**
 * Copyright (C) 2015 Michael Schnell. All rights reserved. 
 * http://www.fuin.org/
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. If not, see http://www.gnu.org/licenses/.
 */
package org.fuin.code2svg.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.fuin.code2svg.core.Code2Svg;
import org.fuin.code2svg.core.Code2SvgConfig;
import org.fuin.code2svg.core.Code2SvgUtils;
import org.fuin.ext4logback.NewLogConfigFileParams;
import org.fuin.utils4j.JaxbUtils;
import org.fuin.utils4j.Utils4J;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Converts a source file into an SVG file.
 */
public final class Code2SvgApp {

    private static final Logger LOG = LoggerFactory.getLogger(Code2SvgApp.class);

    private static URL url(File file) {
        try {
            return file.toURI().toURL();
        } catch (final MalformedURLException ex) {
            throw new RuntimeException("Failed to convert file to URL: " + file, ex);
        }
    }

    private static void execute(final File configFile, final File targetDir, final List<String> filenames) {

        final String configXml = Utils4J.readAsString(url(configFile), "utf-8", 1024);
        final Code2SvgConfig config = JaxbUtils.unmarshal(configXml, Code2SvgUtils.JAXB_CLASSES);

        final Code2Svg converter = new Code2Svg();
        filenames.forEach(filename -> {
            final File file = new File(filename);
            if (file.isDirectory()) {
                converter.convertDir(config, file, targetDir);
            } else {
                converter.convertFile(config, file.getParentFile(), file, targetDir);
            }
        });

    }

    private static void writeInitialLogbackXml(final File logbackXmlFile, final NewLogConfigFileParams params) throws IOException {
        final String logFilename = params.getLogFilename();
        // @formatter:off
        final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" 
                + "<configuration>\n"
                + "    <appender name=\"FILE\" class=\"ch.qos.logback.core.rolling.RollingFileAppender\">\n"
                + "        <file>${log_path}/" + logFilename + ".log</file>\n"
                + "        <rollingPolicy class=\"ch.qos.logback.core.rolling.TimeBasedRollingPolicy\">\n"
                + "            <fileNamePattern>" + logFilename + ".%d{yyyy-MM-dd}.log</fileNamePattern>\n"
                + "            <maxHistory>30</maxHistory>\n" 
                + "        </rollingPolicy>\n"
                + "        <append>true</append>\n"
                + "        <layout class=\"ch.qos.logback.classic.PatternLayout\">\n"
                + "            <pattern>" + params.getLayoutPattern() + "</pattern>\n" 
                + "        </layout>\n"
                + "    </appender>\n"
                + "    <appender name=\"CONSOLE\" class=\"ch.qos.logback.core.ConsoleAppender\">"
                + "        <layout class=\"ch.qos.logback.classic.PatternLayout\">"
                + "            <pattern>" + params.getLayoutPattern() + "</pattern>\n"
                + "        </layout>"
                + "    </appender>"                
                + "    <root level=\"" + params.getRootLevel() + "\">\n"
                + "        <appender-ref ref=\"FILE\" />\n" 
                + "        <appender-ref ref=\"CONSOLE\" />\n" 
                + "    </root>\n" 
                + "    <logger name=\"" + params.getPkgName() + "\" level=\"" + params.getPkgLevel() + "\" />\n" 
                + "</configuration>\n";
        // @formatter:on
        try (final Writer fw = new OutputStreamWriter(new FileOutputStream(logbackXmlFile), Charset.forName("UTF-8"))) {
            fw.write(xml);
        }
    }

    private static void initLogback() {
        final File logbackXmlFile = new File("code2svg-logback.xml");
        if (!logbackXmlFile.exists()) {
            try {
                writeInitialLogbackXml(logbackXmlFile, new NewLogConfigFileParams("org.fuin.code2svg.app", "code2svg"));
            } catch (final IOException ex) {
                throw new RuntimeException("Failed to create initial logback XML configuration", ex);
            }
        }
    }

    public static void main(String[] args) {

        if (args == null || args.length < 3) {
            System.out.println("Required arguments: <config-path-and-name> <target dir> <source file or dir 1> ... <source file or dir N>");
            System.exit(1);
        }

        try {
            initLogback();
            LOG.info("Application running...");

            final File configFile = new File(args[0]);
            Utils4J.checkValidFile(configFile);
            final File targetDir = new File(args[1]);
            Utils4J.checkValidDir(targetDir);
            final List<String> filenames = Arrays.asList(Arrays.copyOfRange(args, 2, args.length));

            LOG.info("configFile={}", configFile);
            LOG.info("targetDir={}", targetDir);
            LOG.info("filenames={}", filenames);

            execute(configFile, targetDir, filenames);

            System.exit(0);

        } catch (RuntimeException ex) {
            ex.printStackTrace(System.err);
            System.exit(2);
        }

    }

}
