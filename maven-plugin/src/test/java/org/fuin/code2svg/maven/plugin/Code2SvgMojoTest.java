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
package org.fuin.code2svg.maven.plugin;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.util.FileUtils;
import org.fuin.code2svg.core.Code2Svg;
import org.fuin.code2svg.maven.plugin.Code2SvgMojo;
import org.fuin.utils4j.Utils4J;
import org.junit.Test;

/**
 * Test for {@link EventStoreCertificateMojo}.
 */
public class Code2SvgMojoTest {

    // CHECKSTYLE:OFF Test

    @Test
    public void testExecute() throws MojoExecutionException, IOException {

        // PREPARE
        final File configFile = copy("/code-2-svg.xml", this.getClass().getSimpleName() + "-code-2-svg.xml");        
        final File sourceFile = copy("/Alpha3CountryCode.ddd", this.getClass().getSimpleName() + "-Alpha3CountryCode.ddd");        
        final File targetFile = new File(sourceFile.getParentFile(), sourceFile.getName() + ".svg");

        final Code2SvgMojo testee = new Code2SvgMojo();
        testee.setConfigFile(configFile.toString());
        testee.setSourceFilesDirs(new String[] { sourceFile.toString() });        

        // TEST
        testee.execute();

        // VERIFY
        assertThat(targetFile).exists();
        assertThat(targetFile.length()).isGreaterThan(0);

    }
    
    private static File copy(final String fromResource, final String toFile) {
	try {
        final URL url = Code2Svg.class.getResource(fromResource);
        final File file = File.createTempFile(toFile.toString(), ".tmp");        
        FileUtils.copyURLToFile(url, file);
        return file;
	} catch (final IOException ex) {
	    throw new RuntimeException("Error copying resource '" + fromResource + "' to file '" + toFile + "'", ex);
	}
    }

    // CHECKSTYLE:ON

}
