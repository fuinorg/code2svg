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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.fuin.code2svg.core.Code2Svg;
import org.fuin.code2svg.core.Code2SvgConfig;
import org.fuin.code2svg.core.Code2SvgUtils;
import org.fuin.code2svg.core.RegExprElement;
import org.fuin.utils4j.JaxbUtils;
import org.fuin.utils4j.Utils4J;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;

/**
 * Mojo that converts source files to SVG files with syntax highlighting.
 */
@Mojo(name = "convert", defaultPhase = LifecyclePhase.GENERATE_RESOURCES, requiresProject = false)
public final class Code2SvgMojo extends AbstractMojo {

    private static final Logger LOG = LoggerFactory.getLogger(Code2SvgMojo.class);

    /**
     * Path and file of the configuration XML. Defaults to "code2svg.xml".
     */
    @Parameter(name = "config-file")
    private String config;

    /**
     * List of files or directories to convert. Defaults to "src/main/resources".
     */
    @Parameter(name = "source-files-dirs")
    private String[] sourceFilesDirs;

    /**
     * Checks if a variable is not <code>null</code> and throws an <code>IllegalNullArgumentException</code> if this rule is violated.
     * 
     * @param name
     *            Name of the variable to be displayed in an error message.
     * @param value
     *            Value to check for <code>null</code>.
     * 
     * @throws MojoExecutionException
     *             Checked value was NULL.
     */
    protected final void checkNotNull(final String name, final Object value) throws MojoExecutionException {
        if (value == null) {
            throw new MojoExecutionException(name + " cannot be null!");
        }
    }

    @Override
    public final void execute() throws MojoExecutionException {
        StaticLoggerBinder.getSingleton().setMavenLog(getLog());
        init();

        LOG.info("config={}", config);
        final Object[] args = sourceFilesDirs;
        LOG.info("sourceFiles={}", args);

        final File configFile = new File(config);
        final String configXml = Utils4J.readAsString(url(configFile), "utf-8", 1024);
        final Code2SvgConfig config = JaxbUtils.unmarshal(configXml, (Class<?>[]) Code2SvgUtils.JAXB_CLASSES.toArray());

        LOG.info("Converting '{}' sources to SVG files...", config.getFileExtension());

        final Code2Svg converter = new Code2Svg();
        for (int i = 0; i < sourceFilesDirs.length; i++) {
            final File file = new File(sourceFilesDirs[i]);
            if (file.isDirectory()) {
                converter.convertDir(config, file);
            } else {
                converter.convertFile(config, file);
            }
        }

    }

    private void init() throws MojoExecutionException {
        if (config == null) {
            config = "code2svg.xml";
        }
        if (sourceFilesDirs == null || sourceFilesDirs.length == 0) {
            sourceFilesDirs = new String[] { "src/main/resources" };
        }
    }

    /**
     * Returns the file with the XML configuration.
     * 
     * @return File.
     * 
     * @throws MojoExecutionException
     *             Error initializing the variable.
     */
    public final String getConfigFile() throws MojoExecutionException {
        if (config == null) {
            init();
        }
        return config;
    }

    /**
     * Sets the path and name of the configuration file.
     * 
     * @param configFile
     *            Config file to use.
     */
    public final void setConfigFile(final String configFile) {
        this.config = configFile;
    }

    /**
     * Returns the paths and names of source files or directories.
     * 
     * @return File array.
     */
    public final String[] getSourceFilesDirs() {
        return sourceFilesDirs;
    }

    /**
     * Sets the path and names of source files or directories to convert.
     * 
     * @param sourceFilesDirs
     *            File array.
     */
    public void setSourceFilesDirs(String[] sourceFilesDirs) {
        this.sourceFilesDirs = sourceFilesDirs;
    }

    private static URL url(File file) {
        try {
            return file.toURI().toURL();
        } catch (final MalformedURLException ex) {
            throw new RuntimeException("Failed to convert file to URL: " + file, ex);
        }
    }

}
