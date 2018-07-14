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

import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParsingException;
import javax.validation.constraints.NotNull;

import org.fuin.utils4j.Utils4J;

/**
 * Parses configuration elements from a model.
 */
public final class ModelConfigParser {

    static final String CODE2SVG_KEY = "@code2svg:";
    
    static final Pattern PATTERN = Pattern.compile(Pattern.quote(CODE2SVG_KEY) + "\\{.*?\\}");
    
    private final Code2SvgConfig config;

    /**
     * Constructor with all mandatory data.
     * 
     * @param config
     *            Configuration.
     */
    public ModelConfigParser(@NotNull final Code2SvgConfig config) {
        super();
        Utils4J.checkNotNull("config", config);
        this.config = config;
    }

    /**
     * Parses the model and returns a configuration updated with values from the JSON,
     * 
     * @param model
     *            Model to parse for {@link #CODE2SVG_KEY} followed by a valid JSON object.
     * 
     * @return Updated configuration.
     */
    public final Code2SvgConfig parse(final String model) {
        final String json = extractJson(model);
        if (json == null) {
            return config;
        }
        final Code2SvgConfig.Builder builder = new Code2SvgConfig.Builder();
        builder.copy(config);
        final JsonObject jsonObj = parseJson(json);
        if (jsonObj.containsKey("width")) {
            builder.width(jsonObj.getInt("width"));
        }
        if (jsonObj.containsKey("height")) {
            builder.height(jsonObj.getInt("height"));
        }
        if (jsonObj.containsKey("text-css")) {
            builder.textCss(jsonObj.getString("text-css"));
        }
        return builder.build();
    }

    static String extractJson(final String model) {
        final Matcher matcher = PATTERN.matcher(model);
        if (matcher.find()) {
            final String all = model.substring(matcher.start(), matcher.end());
            return all.substring(CODE2SVG_KEY.length());
        }
        return null;
    }
    
    static JsonObject parseJson(final String json) {
        final JsonReader reader = Json.createReaderFactory(null).createReader(new StringReader(json));
        try {
            return reader.readObject();
        } catch (final JsonParsingException ex) {
            throw new RuntimeException("Error parsing JSON: '" + json + "'", ex);
        }
    }

}
