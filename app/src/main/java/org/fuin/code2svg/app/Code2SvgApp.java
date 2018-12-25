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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import org.fuin.code2svg.core.Code2Svg;
import org.fuin.code2svg.core.Code2SvgConfig;
import org.fuin.code2svg.core.Code2SvgUtils;
import org.fuin.ext4logback.LogbackStandalone;
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

    private static void execute(final File configFile, final File targetDir, final String[] args) {

        final String configXml = Utils4J.readAsString(url(configFile), "utf-8", 1024);
        final Code2SvgConfig config = JaxbUtils.unmarshal(configXml, (Class<?>[]) Code2SvgUtils.JAXB_CLASSES.toArray());

        final Code2Svg converter = new Code2Svg();
        for (int i = 1; i < args.length; i++) {
            final File file = new File(args[i]);
            if (file.isDirectory()) {
                converter.convertDir(config, file, targetDir);
            } else {
                converter.convertFile(config, file.getParentFile(), file, targetDir);
            }
        }

    }

    public static void main(String[] args) {

        if (args == null || args.length < 3) {
            System.out.println("Required arguments: <config-path-and-name> <target dir> <source file or dir 1> ... <source file or dir N>");
            System.exit(1);
        }

        try {
            new LogbackStandalone().init(new File("code2svg"), new NewLogConfigFileParams("org.fuin.code2svg.app", "code2svg"));
            LOG.info("Application running...");

            final File configFile = new File(args[0]);
            Utils4J.checkValidFile(configFile);
            final File targetDir = new File(args[1]);
            Utils4J.checkValidDir(targetDir);
            final String[] args2 = Arrays.copyOfRange(args, 2, args.length - 1);

            LOG.info("configFile={}", configFile);
            LOG.info("targetDir={}", targetDir);
            LOG.info("args2={}", Arrays.asList(args2));

            execute(configFile, targetDir, args2);

            System.exit(0);

        } catch (RuntimeException ex) {
            ex.printStackTrace(System.err);
            System.exit(2);
        }

    }

}
