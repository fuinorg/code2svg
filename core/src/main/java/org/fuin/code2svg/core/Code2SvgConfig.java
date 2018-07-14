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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "code2svg")
public class Code2SvgConfig {

    @Nullable
    @XmlAttribute(name = "file-extension")
    private String fileExtension;

    @Nullable
    @XmlAttribute(name = "width")
    private Integer width;

    @Nullable
    @XmlAttribute(name = "height")
    private Integer height;

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
         * @param width
         *            Default height.
         * 
         * @return Builder.
         */
        public final Builder height(@Nullable final Integer height) {
            config.height = height;
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
         * Returns the configuration.
         * 
         * @return Config.
         */
        public Code2SvgConfig build() {
            final Code2SvgConfig result = config;
            config = new Code2SvgConfig();
            return result;
        }

    }

}
