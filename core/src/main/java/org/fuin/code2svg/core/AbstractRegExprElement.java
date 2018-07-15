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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Base class for elements that can be found using a regular expression.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractRegExprElement extends AbstractElement {

    private transient Pattern pattern;

    /**
     * Package visible default constructor for deserialization.
     */
    AbstractRegExprElement() { // NOSONAR Ignore not initialized fields
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
    public AbstractRegExprElement(final String name, final String css) {
        super(name, css);
    }

    @Override
    public final ElementMatcher matcher(final String text) {
        return new RegExprMatcher(getCompiledPattern().matcher(text));
    }

    /**
     * Returns the compiled pattern.
     * 
     * @return Pattern.
     */
    public final Pattern getCompiledPattern() {
        if (pattern == null) {
            pattern = Pattern.compile(getPattern());
        }
        return pattern;
    }

    /**
     * Returns the pattern.
     * 
     * @return Pattern.
     */
    public abstract String getPattern();

}
