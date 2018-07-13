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
package org.fuin.code2svg.app;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import org.fuin.code2svg.core.Code2Svg;
import org.fuin.code2svg.core.Code2SvgConfig;
import org.fuin.code2svg.core.RegExprElement;
import org.fuin.ext4logback.LogbackStandalone;
import org.fuin.ext4logback.NewLogConfigFileParams;
import org.fuin.utils4j.JaxbUtils;
import org.fuin.utils4j.Utils4J;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Converts a source file into an SVG file.
 */
public final class Code2SvgApp {

    private static final Logger LOG = LoggerFactory.getLogger(Code2SvgApp.class);
    
    private static URL url(File file) {
	try {
	    return file.toURI().toURL();
	} catch (final MalformedURLException ex) {
	    throw new RuntimeException("Failed to convert file to URL: " + file,
		    ex);
	}
    }
    
    private static void execute(final String[] args) {
	
	final File configFile = new File(args[0]);
	final String configXml = Utils4J.readAsString(url(configFile), "utf-8",
		1024);
	final Code2SvgConfig config = JaxbUtils.unmarshal(configXml,
		Code2SvgConfig.class, RegExprElement.class);

	final Code2Svg converter = new Code2Svg();
	for (int i = 1; i < args.length; i++) {
	    final File file = new File(args[i]);
	    if (file.isDirectory()) {
		converter.convertDir(config, file);
	    } else {
		converter.convertFile(config, file);
	    }
	}
	
    }

    public static void main(String[] args) {

	if (args == null || args.length < 2) {
	    System.out.println(
		    "Required arguments: <config-path-and-name> <file or dir 1> ... <file or dir N>");
	    System.exit(1);
	}

	    try {
	        // Initializes Logback by reading the XML config file.
	        // If the file does not exist, it will be created with some defaults.
	        // This is a convenience method that directly uses the main method's arguments.
		final String[] args2 = Arrays.copyOfRange(args, 1, args.length - 1);
	        new LogbackStandalone().init(args2, new NewLogConfigFileParams("org.fuin.code2svg.app", "code-2-svg.log"));
	        LOG.info("Application running...");
	        execute(args);
	        System.exit(0);
	    } catch (RuntimeException ex) {
	        ex.printStackTrace(System.err);
	        System.exit(1);
	    }

    }

}
