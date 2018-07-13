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

import java.util.regex.Matcher;

/**
 * Element matcher that uses a regular expression to find the element.
 */
public final class RegExprMatcher implements ElementMatcher {

    private final Matcher matcher;

    /**
     * Constructor with pattern matcher.
     * 
     * @param matcher
     *            Matcher to use.
     */
    public RegExprMatcher(final Matcher matcher) {
        super();
        this.matcher = matcher;
    }

    @Override
    public boolean find() {
        return matcher.find();
    }

    @Override
    public int start() {
        return matcher.start();
    }

    @Override
    public int end() {
        return matcher.end();
    }

    @Override
    public void appendReplacement(StringBuffer sb, String replacement) {
        matcher.appendReplacement(sb, replacement);
    }

    @Override
    public void appendTail(final StringBuffer sb) {
        matcher.appendTail(sb);
    }

}
