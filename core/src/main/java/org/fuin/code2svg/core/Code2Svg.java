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
package org.fuin.code2svg.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.fuin.utils4j.Utils4J;
import org.fuin.utils4j.fileprocessor.FileHandler;
import org.fuin.utils4j.fileprocessor.FileHandlerResult;
import org.fuin.utils4j.fileprocessor.FileProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Converts a source file into an SVG file.
 */
public final class Code2Svg {

    private static final Logger LOG = LoggerFactory.getLogger(Code2Svg.class);

    private static String XML_PREFIX = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\" ?>";

    private static String DOC_TYPE = "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">";

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private String tagAll(final String source, final List<Element> elements) {
        String src = source;
        for (final Element element : elements) {
            src = tag(src, element);
        }
        return src;
    }

    private String tag(final String source, final Element el) {
        final ElementMatcher m = el.matcher(source);
        final StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String found = source.substring(m.start(), m.end());
            found = found.replace(LINE_SEPARATOR, el.getSvgEndTag() + LINE_SEPARATOR + el.getSvgStartTag());
            m.appendReplacement(sb, el.getSvgStartTag() + found + el.getSvgEndTag());
        }
        m.appendTail(sb);
        return sb.toString();
    }

    private void writeToFile(final File file, final String src, final String title, final String description,
            final List<Element> elements) {

        try (final Writer writer = new BufferedWriter(new FileWriter(file))) {

            writer.write(XML_PREFIX + LINE_SEPARATOR);
            writer.write(DOC_TYPE + LINE_SEPARATOR);
            writer.write("<svg version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">" + LINE_SEPARATOR);
            writer.write("<title>" + title + "</title>" + LINE_SEPARATOR);
            writer.write("<desc>" + description + "</desc>" + LINE_SEPARATOR);
            writer.write("<style>" + LINE_SEPARATOR);
            writer.write("<![CDATA[" + LINE_SEPARATOR);
            writer.write("text { font-size: 11pt; font-family: monospace }" + LINE_SEPARATOR);
            for (final Element element : elements) {
                writer.write("." + element.getName() + " { " + element.getCSS() + " }" + LINE_SEPARATOR);
            }
            writer.write("]]>" + LINE_SEPARATOR);
            writer.write("</style>" + LINE_SEPARATOR);
            writer.write("<text xml:space=\"preserve\">" + LINE_SEPARATOR);
            writer.write(src + LINE_SEPARATOR);
            writer.write("</text>" + LINE_SEPARATOR);
            writer.write("</svg>" + LINE_SEPARATOR);

        } catch (final IOException ex) {
            throw new RuntimeException("Error writing file: " + file, ex);
        }
    }

    private String convert(final Code2SvgConfig config, final String model) {
        String src = tagAll(model, config.getElements());
        final String lineStart = "<tspan dy=\"1.2em\" x=\"10\"> </tspan>";
        src = lineStart + src.replace(LINE_SEPARATOR, LINE_SEPARATOR + lineStart);
        src = src.replace("\t", "    ");
        return src;
    }

    public void convertFile(final Code2SvgConfig config, final File srcFile) {

        LOG.info("READ {}", srcFile);

        final File targetFile = new File(srcFile.getParentFile(), srcFile.getName() + ".svg");
        final String model = Utils4J.readAsString(url(srcFile), "utf-8", 1024);

        String src = convert(config, model);
        String title = srcFile.getName();
        String description = "Converted from " + srcFile.getName() + " to " + targetFile.getName();

        writeToFile(targetFile, src, title, description, config.getElements());

        LOG.info("WRITE {}", targetFile);

    }

    public void convertDir(final Code2SvgConfig config, final File srcDir) {
        new FileProcessor(new FileHandler() {
            @Override
            public FileHandlerResult handleFile(final File file) {
                if (file.getName().endsWith(config.getFileExtension())) {
                    convertFile(config, file);
                }
                return FileHandlerResult.CONTINUE;
            }
        }).process(srcDir);
    }

    private static URL url(File file) {
        try {
            return file.toURI().toURL();
        } catch (final MalformedURLException ex) {
            throw new RuntimeException("Failed to convert file to URL: " + file, ex);
        }
    }

}
