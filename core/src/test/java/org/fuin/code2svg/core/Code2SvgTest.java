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

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.fuin.utils4j.JaxbUtils;
import org.fuin.utils4j.Utils4J;
import org.junit.Test;

/**
 * Test for {@link Code2Svg}.
 */
public class Code2SvgTest {

    @Test
    public void testConvertFile() throws IOException {

        // PREPARE
        final File configFile = copy("/code-2-svg.xml", "code-2-svg.xml");
        final File sourceFile = copy("/Alpha3CountryCode.ddd", "Alpha3CountryCode.ddd");
        final File actualTargetFile = new File(sourceFile.getParentFile(), sourceFile.getName() + ".svg");
        final File expectedTargetFile = copy("/Alpha3CountryCode.ddd.svg", "Alpha3CountryCode-expected.ddd.svg");
        final String configXml = Utils4J.readAsString(url(configFile), "utf-8", 1024);
        final Code2SvgConfig config = JaxbUtils.unmarshal(configXml, (Class<?>[]) Code2SvgUtils.JAXB_CLASSES.toArray());

        final Code2Svg testee = new Code2Svg();

        // TEST
        testee.convertFile(config, sourceFile);

        // VERIFY
        assertThat(actualTargetFile).hasSameContentAs(expectedTargetFile);

    }

    @Test
    public void testConvertFile2() throws IOException {

        // PREPARE
        final File configFile = copy("/code-2-svg-2.xml", "code-2-svg.xml");
        final File sourceFile = copy("/Alpha3CountryCode2.ddd", "Alpha3CountryCode.ddd");
        final File actualTargetFile = new File(sourceFile.getParentFile(), sourceFile.getName() + ".svg");
        final File expectedTargetFile = copy("/Alpha3CountryCode2.ddd.svg", "Alpha3CountryCode-expected.ddd.svg");
        final String configXml = Utils4J.readAsString(url(configFile), "utf-8", 1024);
        final Code2SvgConfig config = JaxbUtils.unmarshal(configXml, (Class<?>[]) Code2SvgUtils.JAXB_CLASSES.toArray());

        final Code2Svg testee = new Code2Svg();

        // TEST
        testee.convertFile(config, sourceFile);

        // VERIFY
        assertThat(actualTargetFile).hasSameContentAs(expectedTargetFile);

    }

    @Test
    public void testConvert() throws IOException {

        // PREPARE
        final URL url = Code2Svg.class.getResource("/code-2-svg.xml");
        final String configXml = Utils4J.readAsString(url, "utf-8", 1024);
        final Code2SvgConfig config = JaxbUtils.unmarshal(configXml, (Class<?>[]) Code2SvgUtils.JAXB_CLASSES.toArray());

        final Code2Svg testee = new Code2Svg();

        // TEST
        final String result = testee.convert(config, "     slabel \"ALPHA3CC\" // Whatever slabel 123");

        // VERIFY
        assertThat(result).isEqualTo(
                "<tspan dy=\"1.2em\" x=\"10\"> </tspan>     <tspan class=\"keyword\">slabel</tspan> <tspan class=\"string\">&quot;ALPHA3CC&quot;</tspan> <tspan class=\"sl-comment\">// Whatever slabel 123</tspan>");

    }

    private static File copy(final String fromResource, final String toFile) {
        try {
            final URL url = Code2Svg.class.getResource(fromResource);
            final File file = new File(Utils4J.getTempDir(), toFile.toString());
            FileUtils.copyURLToFile(url, file);
            return file;
        } catch (final IOException ex) {
            throw new RuntimeException("Error copying resource '" + fromResource + "' to file '" + toFile + "'", ex);
        }
    }

    private static URL url(File file) {
        try {
            return file.toURI().toURL();
        } catch (final MalformedURLException ex) {
            throw new RuntimeException("Failed to convert file to URL: " + file, ex);
        }
    }

}
