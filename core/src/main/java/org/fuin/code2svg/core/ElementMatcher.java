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

/**
 * Matcher used to find a piece an element in a text.
 */
public interface ElementMatcher {

    /**
     * Attempts to find the next element.
     * 
     * @return If an element was found <code>true</code> else <code>false</code>.
     */
    public boolean find();

    /**
     * Returns the start index of the previous match.
     * 
     * @return Start index.
     */
    public int start();

    /**
     * Returns the offset after the last character matched.
     * 
     * @return End index.
     */
    public int end();

    /**
     * Implements a non-terminal append-and-replace step. See {@link java.util.regex.Matcher#appendReplacement(StringBuffer, String)}.
     * 
     * @param sb
     *            The target string buffer.
     * @param replacement
     *            The replacement string.
     */
    public void appendReplacement(StringBuffer sb, String replacement);

    /**
     * Implements a terminal append-and-replace step. See {@link java.util.regex.Matcher#appendTail(StringBuffer)}.
     * 
     * @param sb
     *            The target string buffer.
     */
    public void appendTail(StringBuffer sb);

}
