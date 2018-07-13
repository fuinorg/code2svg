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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.fuin.utils4j.Utils4J;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "code2svg")
public class Code2SvgConfig {

    @NotNull
    @XmlAttribute(name = "file-extension")
    private String fileExtension;
    
    @NotEmpty
    @Valid
    @XmlAnyElement(lax = true)
    private List<Element> elements;
    
    /**
     * Package visible default constructor for deserialization.
     */
    Code2SvgConfig() { //NOSONAR Ignore not initialized fields
        super();
    }

    /**
     * Constructor with all mandatory data.
     * 
     * @param fileExtension
     *            Extension of code files.
     * @param elements
     *            List of elements.
     */
    public Code2SvgConfig(final String fileExtension, final Element...elements) {
        super();
        Utils4J.checkNotEmpty("fileExtension", fileExtension);
        if (elements == null || elements.length == 0) {
            throw new IllegalArgumentException("elements cannot be empty or null");
        }
        this.fileExtension = fileExtension;
        this.elements = Arrays.asList(elements);
    }

    /**
     * Returns the file extension.
     * 
     * @return Code file extension.
     */
    public final String getFileExtension() {
        return fileExtension;
    }

    /**
     * Returns a list of all elements.
     * 
     * @return Immutable list.
     */
    public final List<Element> getElements() {
        return Collections.unmodifiableList(elements);
    }
    
    
    
}
