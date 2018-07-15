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
import java.util.List;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Keywords element.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "keyword-element")
public final class KeywordElement extends AbstractRegExprElement {

    @Nullable
    @XmlElement(name = "keyword")
    private List<String> keywords;

    /**
     * Package visible default constructor for deserialization.
     */
    KeywordElement() { // NOSONAR Ignore not initialized fields
        super();
    }

    /**
     * Constructor with all mandatory data.
     * 
     * @param name
     *            Unique name.
     * @param css
     *            Style.
     * @param keywords
     *            Lists of keywords to create a regular expression from.
     */
    public KeywordElement(final String name, final String css, final List<String> keywords) {
        super(name, css);
        this.keywords = new ArrayList<>(keywords);
    }

    /**
     * Returns the pattern.
     * 
     * @return Pattern.
     */
    public final String getPattern() {
        return keywords2expression(keywords);
    }

    /**
     * Returns the keywords list.
     * 
     * @return List of keywords.
     */
    public List<String> getKeywords() {
        return keywords;
    }

    @Override
    public String toString() {
        return "RegExprElement [name=" + getName() + ", css=" + getCSS() + ", keywords=" + keywords + "]";
    }

    private static String keywords2expression(final List<String> keywords) {
        final String notWithinQuotesExpression = "(?=([^\"\\\\]*(\\\\.|\"([^\"\\\\]*\\\\.)*[^\"\\\\]*\"))*[^\"]*$)";
        final StringBuilder sb = new StringBuilder();
        for (final String keyword : keywords) {
            if (sb.length() > 0) {
                sb.append("|");
            }
            sb.append("(\\b" + keyword + "\\b)" + notWithinQuotesExpression);
        }
        return sb.toString();
    }

}
