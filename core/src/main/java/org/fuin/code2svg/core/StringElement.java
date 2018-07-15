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

import javax.annotation.Nullable;
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Single or double quoted string element.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "string-element")
public class StringElement extends AbstractRegExprElement {

    @Nullable
    @XmlAttribute(name = "single")
    private Boolean single;

    /**
     * Package visible default constructor for deserialization.
     */
    StringElement() { // NOSONAR Ignore not initialized fields
        super();
    }

    /**
     * Constructor with mandatory data.
     * 
     * @param name
     *            Unique name.
     * @param css
     *            Style.
     */
    public StringElement(@NotEmpty final String name, @NotEmpty final String css) {
        super(name, css);
        this.single = null;
    }

    /**
     * Constructor with all data.
     * 
     * @param name
     *            Unique name.
     * @param css
     *            Style.
     * @param single
     *            true if single quoted string, else false or <code>null</code> for double quoted string.
     */
    public StringElement(@NotEmpty final String name, @NotEmpty final String css, @Nullable final Boolean single) {
        super(name, css);
        this.single = single;
    }

    /**
     * Returns the information if single or double quotes are used.
     * 
     * @return true if single quoted string, else false or <code>null</code> for double quoted string.
     */
    public Boolean getSingle() {
        return single;
    }

    /**
     * Returns the information if single or double quotes are used.
     * 
     * @return true if single quoted string, else false for double quoted string.
     */
    public boolean isSingle() {
        if (single == null || !single) {
            return false;
        }
        return true;
    }

    @Override
    public final String getPattern() {
        if (isSingle()) {
            return "'.*?'";
        }
        return "&quot;.*?&quot;";
    }

}
