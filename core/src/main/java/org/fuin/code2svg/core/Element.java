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
 * Describes an element to locate in the text file.
 */
public interface Element {

    /**
     * Returns the unique name of this element. The name must be compliant with a CSS class selector name.
     * 
     * @return Name of the element.
     */
    public String getName();

    /**
     * Returns the CSS to be used for that element.
     * 
     * @return Class selector CSS.
     */
    public String getCSS();

    /**
     * Returns a matcher that is capable to locate this element.
     * 
     * @param text
     *            Text to search for elements.
     * 
     * @return Matcher that can be used to find the element in a text.
     */
    public ElementMatcher matcher(String text);

    /**
     * Returns the SVG start tag for the element.
     * 
     * @return Start tag like '&lt;tspan class=&quot;keyword&quot;&gt;'
     */
    public String getSvgStartTag();

    /**
     * Returns the SVG end tag for the element.
     * 
     * @return End tag like '&lt;/tspan&gt;'
     */
    public String getSvgEndTag();

}
