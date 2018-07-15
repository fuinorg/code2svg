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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Multiple line comment element.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ml-comment-element")
public class MultiLineCommentElement extends AbstractRegExprElement {

    /**
     * Package visible default constructor for deserialization.
     */
    MultiLineCommentElement() { // NOSONAR Ignore not initialized fields
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
    public MultiLineCommentElement(@NotEmpty final String name, @NotEmpty final String css) {
        super(name, css);
    }

    @Override
    public final String getPattern() {
        return "/\\*(.|[\\r\\n])*?\\*/";
    }

}
