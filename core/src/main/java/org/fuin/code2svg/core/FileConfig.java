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

import java.util.regex.Pattern;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Keywords element.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "file-config")
public final class FileConfig {

    @NotNull
    @XmlAttribute(name = "name")
    private String name;

    @Nullable
    @XmlAttribute(name = "width")
    private Integer width;

    @Nullable
    @XmlAttribute(name = "height")
    private Integer height;

    @XmlTransient
    private Pattern compiledName;

    /**
     * Package visible default constructor for deserialization.
     */
    FileConfig() { // NOSONAR Ignore not initialized fields
        super();
    }

    /**
     * Constructor with all fields.
     * 
     * @param name
     *            Regular expression with path and file name.
     * @param width
     *            Width to apply for the file in SVG.
     * @param height
     *            Height to apply for the file in SVG.
     */
    public FileConfig(@NotNull String name, @Nullable Integer width, @Nullable Integer height) {
        super();
        this.name = name;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns the regular expression with path and file name.
     * 
     * @return Expression to find a file.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the width to apply for the file in SVG.
     * 
     * @return Width.
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * Returns the height to apply for the file in SVG.
     * 
     * @return Height.
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * Returns the compiled name pattern.
     * 
     * @return Name pattern.
     */
    public final Pattern getCompiledName() {
        if (compiledName == null) {
            compiledName = Pattern.compile(name);
        }
        return compiledName;
    }

    @Override
    public final String toString() {
        return "FileConfig [name=" + name + ", width=" + width + ", height=" + height + "]";
    }

}
