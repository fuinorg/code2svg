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

import javax.validation.constraints.NotNull;

import org.fuin.utils4j.Utils4J;

/**
 * A piece of text that and it's position.
 */
public final class PieceOfText {

    private final String text;

    private final int start;

    private final int end;

    /**
     * Constructor with all data.
     * 
     * @param text
     *            Text.
     * @param start
     *            Start position in the source.
     * @param end
     *            End position in the source.
     */
    public PieceOfText(@NotNull final String text, int start, int end) {
        super();
        Utils4J.checkNotNull("text", text);
        this.text = text;
        this.start = start;
        this.end = end;
    }

    /**
     * Returns the text.
     * 
     * @return Text.
     */
    public final String getText() {
        return text;
    }

    /**
     * Returns the start position in the source.
     * 
     * @return Start position where the text was found.
     */
    public final int getStart() {
        return start;
    }

    /**
     * Returns the end position in the source.
     * 
     * @return End position where the text was found.
     */
    public final int getEnd() {
        return end;
    }

    /**
     * Determines if the given segment overlaps with this one.
     * 
     * @param start
     *            Start of the other segment.
     * @param end
     *            End of the other segment.
     * 
     * @return TRUE if this and the other segment overlap-
     */
    public final boolean overlaps(final int start, final int end) {
        return overlaps(this.start, this.end, start, end) || 
                overlaps(start, end, this.start, this.end);
    }

    private final static boolean overlaps(final int aStart, final int aEnd, final int bStart, final int bEnd) {
        return ((bStart <= aStart) && bEnd >= aStart && bEnd <= aEnd)
                || ((bStart >= aStart) && (bStart <= aEnd) && bEnd >= aEnd)
                || ((bStart >= aStart) && (bEnd <= aEnd));
    }
    
    @Override
    public final String toString() {
        return "PieceOfText [text=" + text + ", start=" + start + ", end=" + end + "]";
    }

}
