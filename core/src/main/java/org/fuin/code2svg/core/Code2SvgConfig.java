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

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;

import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "code2svg")
public class Code2SvgConfig {

    private static final String DEFAULT_TEXT_CSS = "font-size: 11pt; font-family: monospace";

    @Nullable
    @XmlAttribute(name = "file-extension")
    private String fileExtension;

    @Nullable
    @XmlAttribute(name = "width")
    private Integer width;

    @Nullable
    @XmlAttribute(name = "height")
    private Integer height;

    @NotNull
    @XmlAttribute(name = "text-css")
    private String textCss;

    @Nullable
    @XmlElementWrapper(name = "file-configs")
    @XmlElement(name = "file-config")
    private List<FileConfig> fileConfigs;

    @NotEmpty
    @Valid
    @XmlAnyElement(lax = true)
    private List<Element> elements;

    /**
     * Package visible default constructor for deserialization.
     */
    Code2SvgConfig() { // NOSONAR Ignore not initialized fields
        super();
    }

    /**
     * Returns the file extension.
     * 
     * @return Code file extension.
     */
    @Nullable
    public final String getFileExtension() {
        return fileExtension;
    }

    /**
     * Returns the default width for created SVG images.
     * 
     * @return Default width.
     */
    @Nullable
    public final Integer getWidth() {
        return width;
    }

    /**
     * Returns the default height for created SVG images.
     * 
     * @return Default height.
     */
    @Nullable
    public final Integer getHeight() {
        return height;
    }

    /**
     * Returns the CSS for the text.
     * 
     * @return CSS like "font-size: 11pt; font-family: monospace"
     */
    @NotNull
    public final String getTextCss() {
        return textCss;
    }

    /**
     * Returns a list of all file configurations.
     * 
     * @return Immutable list or <code>null</code>.
     */
    @NotNull
    public final List<FileConfig> getFileConfigs() {
        if (fileConfigs == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(fileConfigs);
    }

    /**
     * Adds a file configuration. Creates the internal list of elements if it is not set yet.
     * 
     * @param fileConfig
     *            File configuration to add.
     */
    public final void addFileConfig(final FileConfig fileConfig) {
        if (fileConfigs == null) {
            fileConfigs = new ArrayList<>();
        }
        fileConfigs.add(fileConfig);
    }

    /**
     * Returns the file configuration to use for a given file.
     * 
     * @param file
     *            File to find a configuration for.
     * 
     * @return File configuration or <code>null</code> if no matching was found.
     */
    @Nullable
    public final FileConfig findFor(final File file) {
        final List<FileConfig> list = getFileConfigs();
        for (final FileConfig config : list) {
            final Matcher matcher = config.getCompiledName().matcher(file.getAbsolutePath());
            if (matcher.matches()) {
                return config;
            }
        }
        return null;
    }

    /**
     * Returns a list of all elements.
     * 
     * @return Immutable list.
     */
    @NotNull
    public final List<Element> getElements() {
        return Collections.unmodifiableList(elements);
    }

    /**
     * Adds an element. Creates the internal list of elements if it is not set yet.
     * 
     * @param element
     *            Elements to add.
     */
    public final void addElement(final Element element) {
        if (elements == null) {
            elements = new ArrayList<>();
        }
        elements.add(element);
    }

    public void afterUnmarshal(final Unmarshaller unmarshaller, final Object parent) {
        if (textCss == null) {
            textCss = DEFAULT_TEXT_CSS;
        }
    }

    @Override
    public final String toString() {
        return "Code2SvgConfig [fileExtension=" + fileExtension + ", width=" + width + ", height=" + height + ", elements=" + elements
                + "]";
    }

    /**
     * Builder for the configuration.
     */
    public static final class Builder {

        private Code2SvgConfig config;

        /**
         * Default constructor.
         */
        public Builder() {
            super();
            this.config = new Code2SvgConfig();
        }

        /**
         * Copies all attributes.
         * 
         * @param other
         *            Instance to copy.
         * 
         * @return Builder.
         */
        public final Builder copy(@NotNull final Code2SvgConfig other) {
            config.height = other.height;
            config.width = other.width;
            config.fileExtension = other.fileExtension;
            config.textCss = other.textCss;
            if (other.fileConfigs != null) {
                config.fileConfigs = new ArrayList<>(other.fileConfigs);
            }
            config.elements = new ArrayList<>(other.elements);
            return this;
        }

        /**
         * Sets the file extension.
         * 
         * @param fileExtension
         *            Extension.
         * 
         * @return Builder.
         */
        public Builder fileExtension(@Nullable final String fileExtension) {
            config.fileExtension = fileExtension;
            return this;
        }

        /**
         * Sets the default width for created SVG images.
         * 
         * @param width
         *            Default width.
         * 
         * @return Builder.
         */
        public final Builder width(@Nullable final Integer width) {
            config.width = width;
            return this;
        }

        /**
         * Sets the default height for created SVG images.
         * 
         * @param height
         *            Default height.
         * 
         * @return Builder.
         */
        public final Builder height(@Nullable final Integer height) {
            config.height = height;
            return this;
        }

        /**
         * Sets the text CSS.
         * 
         * @param textCss
         *            CSS to use for text like "font-size: 12pt; font-family: monospace"
         * 
         * @return Builder
         */
        public final Builder textCss(@Nullable final String textCss) {
            config.textCss = textCss;
            return this;
        }

        /**
         * Sets the file configurations.
         * 
         * @param fileConfigs
         *            File configurations.
         * 
         * @return Builder.
         */
        public final Builder fileConfigs(@NotNull final List<FileConfig> fileConfigs) {
            config.fileConfigs = new ArrayList<>(fileConfigs);
            return this;
        }

        /**
         * Adds an file configuration.
         * 
         * @param fileConfig
         *            File configuration to add.
         * 
         * @return Builder.
         */
        public final Builder addFileConfig(@NotNull final FileConfig fileConfig) {
            config.addFileConfig(fileConfig);
            return this;
        }

        /**
         * Sets the elements.
         * 
         * @param elements
         *            Elements.
         * 
         * @return Builder.
         */
        public final Builder elements(@NotNull final List<Element> elements) {
            config.elements = new ArrayList<>(elements);
            return this;
        }

        /**
         * Adds an element.
         * 
         * @param element
         *            Element to add.
         * 
         * @return Builder.
         */
        public final Builder addElement(@NotNull final Element element) {
            config.addElement(element);
            return this;
        }

        /**
         * Applies a file configuration.
         * 
         * @param fileConfig Configuration to use the file values (like 'width', 'height') from.
         */
        public final Builder applyConfig(@Nullable final FileConfig fileConfig) {
            if (fileConfig != null) {
                if (fileConfig.getWidth() != null) {
                    width(fileConfig.getWidth());
                }
                if (fileConfig.getHeight() != null) {
                    height(fileConfig.getHeight());
                }
            }
            return this;
        }

        /**
         * Returns the configuration.
         * 
         * @return Config.
         */
        public Code2SvgConfig build() {
            final Code2SvgConfig result = config;
            if (config.textCss == null) {
                config.textCss = DEFAULT_TEXT_CSS;
            }
            config = new Code2SvgConfig();
            return result;
        }

    }

}
