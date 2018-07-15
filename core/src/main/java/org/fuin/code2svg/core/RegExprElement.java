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
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Element that can be found using a regular expression.
 * 
 * The methods hashCode and equals are implemented based on the 'name' attribute.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "reg-expr-element")
public final class RegExprElement extends AbstractElement {

    @Nullable
    @XmlAttribute(name = "pattern")
    private String pattern;

    private transient Pattern rePattern;

    /**
     * Package visible default constructor for deserialization.
     */
    RegExprElement() { // NOSONAR Ignore not initialized fields
        super();
    }

    /**
     * Constructor with regular expression.
     * 
     * @param name
     *            Unique name.
     * @param css
     *            Style.
     * @param regExpr
     *            regular expression.
     */
    public RegExprElement(final String name, final String css, final String regExpr) {
        super(name, css);
        this.pattern = regExpr;
        this.rePattern = Pattern.compile(regExpr);
    }

    /**
     * Returns the pattern.
     * 
     * @return Pattern.
     */
    public final String getPattern() {
        return pattern;
    }

    @Override
    public final ElementMatcher matcher(final String text) {
        return new RegExprMatcher(rePattern.matcher(text));
    }

    public void afterUnmarshal(final Unmarshaller unmarshaller, final Object parent) {
        if (pattern == null) {
            throw new IllegalStateException("Attribute 'pattern' must be set");
        }
        this.rePattern = Pattern.compile(pattern);
    }

    @Override
    public String toString() {
        return "RegExprElement [name=" + getName() + ", css=" + getCSS() + ", pattern=" + pattern + "]";
    }

}
