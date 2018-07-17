# code2svg
A minimalistic converter that transforms code into SVG text with syntax highlighting.

[![Build Status](https://fuin-org.ci.cloudbees.com/job/code2svg/badge/icon)](https://fuin-org.ci.cloudbees.com/job/code2svg/)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.fuin/code2svg/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.fuin/code2svg/)
[![LGPLv3 License](http://img.shields.io/badge/license-LGPLv3-blue.svg)](https://www.gnu.org/licenses/lgpl.html)
[![Java Development Kit 1.8](https://img.shields.io/badge/JDK-1.8-green.svg)](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

## Modules
The project is splitted into three separate modules: The core library, a maven plugin and the standalone application.

- **[Core Library](core)** Java library that can be used within your own programs
- **[Maven Plugin](maven-plugin)** Maven plugin that allows integrating the converter in your build.
- **[Application](app)** Simple Java command line application to run the converter.

## Overview
Example input from [DDD DSL language](https://github.com/fuinorg/org.fuin.dsl.ddd):
```
@code2svg:{"width":800, "height": 300}
/**
 * ISO 3166-1 alpha-3 three-letter country code defined in ISO 3166-1.
 */
value-object °°x2777°° Alpha3CountryCode base String {
    
    label "Alpha-3 country code" // Used in UI 
    
    /** Internal value. */
    String value invariants Length(3, 3)
    
}
```
Example output:
<table><tr><td><img src="https://cdn.rawgit.com/fuinorg/code2svg/089d2faa99e85b1e832a4a2c42b091a0aa9ee46b/example.ddd.svg"></td></tr></table>
(Caution: Image might be broken in Internet Explorer and Edge - Try Firefox or Chromium)

## Configuration

The highlighting is configured using a simple XML file:
```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<code2svg file-extension=".ddd" width="800" height="800">
	<reg-expr-element name="whatever" css="fill: red" pattern="begin.*end" />
	<ml-comment-element name="ml-comment" css="fill: rgb(63, 127, 95)" />
	<sl-comment-element name="sl-comment" css="fill: rgb(63, 127, 95)" />
	<string-element name="string" css="fill: rgb(42, 0, 255)" single="false" />
	<number-element name="number" css="fill: rgb(125, 125, 125)" />
	<keyword-element name="keyword" css="fill: rgb(127, 0, 85); font-weight: bold">
		<keyword>value-object</keyword>
		<keyword>base</keyword>
		<keyword>label</keyword>
		<keyword>invariants</keyword>
	</keyword-element>
</code2svg>
```
- **file-extension** is used for locating the code files inside a directory.
- **width** defines the default width used for all generated SVG images. You can overwrite it by setting a width/height per file (See below). 
- **height** defines the default height used for all generated SVG images. You can overwrite it by setting a width/height per file (See below).
- **name** is a unique identifier that will be used in the SVG markup as a CSS class.
- **css** is the CSS  for the class that will be used to style the element.

### reg-expr-element
Uses a regular expression to identify the part to be styled in the source code.
The **pattern** attribute defines the pattern for locating the code fragments to style.

### ml-comment-element
Locates multi line comments in the source code: 
```
/**
 * My comment
 */ 
```

### sl-comment-element
Locates single line comments in the source code:
```
// Whatever
Integer a
String b // Comment
```

### string-element
Locates a string either with single (**single="true"**) or double quotes (**single="false"** or attribute not present).

### number-element
Used to locate numeric values in the source code.

### keyword-element
Used to locate keywords in the source code.

### Overwrite default width and height
You can overwrite the default width and height from the XML configuration adding ``@code2svg:{"width":800, "height": 300}`` somewhere in the source file.

### Defining XML entity chars
Sometimes it's handy to insert some [XML character entities](https://www.w3schools.com/charsets/ref_utf_dingbats.asp) in the source code.

Example: ``&#x277A;`` can be added as ``°°x277A°°`` in the source and will be rendered as &#x277A;

## Limitations
The order of the tags in the XML configuration determines the order in which source code fragments are replaced.
Elements that are used to locate large blocks of text (like comments) should be evaluated first to avoid finding other elements in that area.
Example: Keywords, strings or numbers that are within a comment.  

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
