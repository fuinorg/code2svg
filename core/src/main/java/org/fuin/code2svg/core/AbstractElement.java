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

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import org.fuin.utils4j.Utils4J;

/**
 * Base class for other elements.
 * 
 * The methods hashCode and equals are implemented based on the 'name' attribute.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractElement implements Element {

    @NotNull
    @XmlAttribute(name = "name")
    private String name;

    @NotNull
    @XmlAttribute(name = "css")
    private String css;

    /**
     * Package visible default constructor for deserialization.
     */
    AbstractElement() { // NOSONAR Ignore not initialized fields
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
    public AbstractElement(@NotEmpty final String name, @NotEmpty final String css) {
        super();
        Utils4J.checkNotEmpty("name", name);
        Utils4J.checkNotEmpty("css", css);
        this.name = name;
        this.css = css;
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final String getCSS() {
        return css;
    }

    @Override
    public final String getSvgStartTag() {
        return "<tspan class=\"" + name + "\">";
    }

    @Override
    public final String getSvgEndTag() {
        return "</tspan>";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AbstractElement other = (AbstractElement) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

}
