# code2svg
A minimalistic converter that transforms code into SVG text with syntax highlighting.

[![Build Status](https://fuin-org.ci.cloudbees.com/job/code2svg/badge/icon)](https://fuin-org.ci.cloudbees.com/job/code2svg/)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.fuin/code2svg/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.fuin/code2svg/)
[![LGPLv3 License](http://img.shields.io/badge/license-LGPLv3-blue.svg)](https://www.gnu.org/licenses/lgpl.html)
[![Java Development Kit 1.8](https://img.shields.io/badge/JDK-1.8-green.svg)](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

## Overview
Example input from [DDD DSL language](https://github.com/fuinorg/org.fuin.dsl.ddd):
```
/**
 * ISO 3166-1 alpha-3 three-letter country code defined in ISO 3166-1.
 */
value-object Alpha3CountryCode base String {
    
    label "Alpha-3 country code" // Used in UI
    
    /** Internal value. */
    String value invariants Length(3, 3)
    
}
```
Example output:
<table><tr><td><img src="https://cdn.rawgit.com/fuinorg/code2svg/cc1d9c65/example.ddd.svg" width="880" height="200"></td></tr></table>



## Configuration

The highlighting is configured using a simple XML file:
```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<code2svg file-extension=".ddd">
	<reg-expr-element name="string" css="fill: rgb(42, 0, 255)" pattern="&quot;.*?&quot;" />
	<reg-expr-element name="ml-comment" css="fill: rgb(63, 127, 95)" pattern="/\*(.|[\r\n])*?\*/" />
	<reg-expr-element name="sl-comment" css="fill: rgb(63, 127, 95)" pattern="//.*[\r\n]" />
	<reg-expr-element name="number" css="fill: rgb(125, 125, 125)" pattern="(\b([\d]+(\.[\d]+)?|0x[a-f0-9]+)\b)(?=([^&quot;\\]*(\\.|&quot;([^&quot;\\]*\\.)*[^&quot;\\]*&quot;))*[^&quot;]*$)" />
	<reg-expr-element name="keyword" css="fill: rgb(127, 0, 85); font-weight: bold">
		<keyword>value-object</keyword>
		<keyword>base</keyword>
		<keyword>label</keyword>
		<keyword>invariants</keyword>
	</reg-expr-element>
</code2svg>
```
- **file-extension** is used for locating the code files inside a directory.
- The ``<reg-expr-element ... />`` is a regular expression based way to identify the part to be styled in the code file.
- **name** is a unique identifier that will be used in the SVG markup as a CSS class.
- **css** is the CSS  for the class that will be used to style the element.
- **pattern** is the pattern for locating the code fragments to style.
- **keyword** is a convenience method to define a pattern for keywords. The regular expression will be built internally.

## Modules
The project is splitted into three separate modules: The core library, a maven plugin and the standalone application.

### Core Library
Java library that can be used within your own programs: [core](core)

### Maven Plugin
Maven plugin that allows integrating the converter in your build: [maven-plugin](maven-plugin)

### Application
Simple Java command line application to run the converter: [app](app)

## Limitations
Currently there is nothing implemented to prevent elements from 'overlapping' each other. 
This means the final SVG image might not exactly be what you want.
In most cases this is not really a problem, and it can be minimized by creating the right regular expressions.

Snapshots
=========

Snapshots can be found on the [OSS Sonatype Snapshots Repository](http://oss.sonatype.org/content/repositories/snapshots/org/fuin "Snapshot Repository"). 

Add the following to your [.m2/settings.xml](http://maven.apache.org/ref/3.2.1/maven-settings/settings.html "Reference configuration") to enable snapshots in your Maven build:

```xml
<repository>
    <id>sonatype.oss.snapshots</id>
    <name>Sonatype OSS Snapshot Repository</name>
    <url>http://oss.sonatype.org/content/repositories/snapshots</url>
    <releases>
        <enabled>false</enabled>
    </releases>
    <snapshots>
        <enabled>true</enabled>
    </snapshots>
</repository>
```
