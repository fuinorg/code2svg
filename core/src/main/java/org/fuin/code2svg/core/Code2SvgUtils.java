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
 * Utility class for the package.
 */
public final class Code2SvgUtils {

    /** Classes used for JAX-B serialization. */
    public static final Class<?>[] JAXB_CLASSES = new Class<?>[] { Code2SvgConfig.class, FileConfig.class, RegExprElement.class,
            StringElement.class, NumberElement.class, SingleLineCommentElement.class, MultiLineCommentElement.class, KeywordElement.class };

    private Code2SvgUtils() {
        throw new UnsupportedOperationException();
    }

}
