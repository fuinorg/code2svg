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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.text.StringEscapeUtils;
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
        final List<Pattern> patternList = createPatternList(elements);
        String src = source;
        for (final Element element : elements) {
            final List<PieceOfText> tagSegments = createList(patternList, src);
            src = tag(tagSegments, src, element);
        }
        return src;
    }

    private String tag(final List<PieceOfText> replacementst, final String source, final Element el) {
        LOG.info("Tagging: {}", el.getName());
        try {
            final ElementMatcher m = el.matcher(source);
            final StringBuffer sb = new StringBuffer();
            while (m.find()) {
                if (!anyOverlaps(replacementst, m.start(), m.end())) {
                    String found = source.substring(m.start(), m.end());
                    found = found.replace("$", "\\$"); // Avoid "No group with name" exception
                    found = found.replace(LINE_SEPARATOR, el.getSvgEndTag() + LINE_SEPARATOR + el.getSvgStartTag());
                    m.appendReplacement(sb, el.getSvgStartTag() + found + el.getSvgEndTag());
                }
            }
            m.appendTail(sb);
            return sb.toString();
        } catch (final RuntimeException ex) {
            LOG.error("Failed finding '{}' elements, source='{}'", el.getName(), source);
            throw ex;
        }
    }

    private void writeToFile(final File file, final String src, final String title, final String description, final Code2SvgConfig config) {

        final List<Element> elements = config.getElements();

        try (final Writer writer = new BufferedWriter(new FileWriter(file))) {

            writer.write(XML_PREFIX + LINE_SEPARATOR);
            writer.write(DOC_TYPE + LINE_SEPARATOR);
            writer.write("<svg version=\"1.1\" " + widthHeighMarkup(config) + "xmlns=\"http://www.w3.org/2000/svg\">" + LINE_SEPARATOR);
            writer.write("<title>" + title + "</title>" + LINE_SEPARATOR);
            writer.write("<desc>" + description + "</desc>" + LINE_SEPARATOR);
            writer.write("<style>" + LINE_SEPARATOR);
            writer.write("<![CDATA[" + LINE_SEPARATOR);
            writer.write("text { " + config.getTextCss() + " }" + LINE_SEPARATOR);
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

    private String widthHeighMarkup(final Code2SvgConfig config) {
        final StringBuilder sb = new StringBuilder();
        if (config.getWidth() != null) {
            sb.append("width=\"");
            sb.append(config.getWidth());
            sb.append("\" ");
        }
        if (config.getHeight() != null) {
            sb.append("height=\"");
            sb.append(config.getHeight());
            sb.append("\" ");
        }
        return sb.toString();
    }

    /**
     * Converts a source string.
     * 
     * @param config
     *            Configuration to use.
     * @param model
     *            Source text to convert.
     * 
     * @return Converted text.
     */
    public String convert(final Code2SvgConfig config, final String model) {

        // Parse and remove inline configuration
        final Matcher matcher = ModelConfigParser.PATTERN.matcher(model);
        String src = matcher.replaceAll("");

        // Escape input
        src = StringEscapeUtils.escapeXml10(src);

        // Replace strings like "°°9986°°" with XML character entity like "&#9986;"
        src = replaceUtf8Characters(src);

        // Tag elements
        src = tagAll(src, config.getElements());

        // Add line start markup
        final String lineStart = "<tspan dy=\"1.2em\" x=\"10\"> </tspan>";
        src = lineStart + src.replace(LINE_SEPARATOR, LINE_SEPARATOR + lineStart);
        src = src.replace("\t", "    ");

        return src;
    }

    /**
     * Converts a source file.
     * 
     * @param config
     *            Configuration to use.
     * @param srcDir
     *            Root directory of the source files. Used to build the relative path of the target SVG file.
     * @param srcFile
     *            File to convert.
     * @param targetDir
     *            Target directory the relative path and SVG file is created inside.
     */
    public void convertFile(final Code2SvgConfig config, final File srcDir, final File srcFile, final File targetDir) {

        LOG.info("READ {}", srcFile);

        final String path = Utils4J.getRelativePath(srcDir, srcFile.getParentFile());
        final File dir = new File(targetDir, path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        final File targetFile = new File(dir, srcFile.getName() + ".svg");
        final String model = Utils4J.readAsString(url(srcFile), "utf-8", 1024);
        final Code2SvgConfig cfg = new ModelConfigParser(config).parse(srcFile, model);

        String src = convert(cfg, model);
        String title = srcFile.getName();
        String description = "Converted from " + srcFile.getName() + " to " + targetFile.getName();

        writeToFile(targetFile, src, title, description, cfg);

        LOG.info("WRITE {}", targetFile);

    }

    /**
     * Convert all files in a directory.
     * 
     * @param config
     *            Configuration to use.
     * @param srcDir
     *            Directory with source files.
     * @param targetDir
     *            Target directory.
     */
    public void convertDir(final Code2SvgConfig config, final File srcDir, final File targetDir) {
        new FileProcessor(new FileHandler() {
            @Override
            public FileHandlerResult handleFile(final File file) {
                if (file.getName().endsWith(config.getFileExtension())) {
                    convertFile(config, srcDir, file, targetDir);
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

    private static boolean anyOverlaps(final List<PieceOfText> replacements, final int start, final int end) {
        for (final PieceOfText replacement : replacements) {
            if (replacement.overlaps(start, end)) {
                return true;
            }
        }
        return false;
    }

    private static List<PieceOfText> createList(final List<Pattern> patternList, final String src) {
        final List<PieceOfText> list = new ArrayList<>();
        for (final Pattern pattern : patternList) {
            final Matcher m = pattern.matcher(src);
            while (m.find()) {
                final String found = src.substring(m.start(), m.end());
                list.add(new PieceOfText(found, m.start(), m.end()));
            }
        }
        return list;
    }

    private static List<Pattern> createPatternList(final List<Element> elements) {
        final List<Pattern> list = new ArrayList<>();
        for (final Element element : elements) {
            list.add(Pattern.compile(Pattern.quote(element.getSvgStartTag()) + ".*" + Pattern.quote(element.getSvgEndTag())));
        }
        return list;
    }

    private static String replaceUtf8Characters(final String src) {
        final Pattern pattern = Pattern.compile("°°\\d+?°°|°°x[0-9A-Fa-f]+?°°");
        final Matcher m = pattern.matcher(src);
        final StringBuffer sb = new StringBuffer();
        while (m.find()) {
            final String code = src.substring(m.start() + 2, m.end() - 2);
            m.appendReplacement(sb, "&#" + code + ";");
        }
        m.appendTail(sb);
        return sb.toString();

    }

}
