#!/bin/bash
java -classpath *:lib/* org.fuin.code2svg.app.Code2SvgApp example/code-2-svg.xml example/Alpha3CountryCode-lf.ddd
echo "Converted 'example/Alpha3CountryCode-lf.ddd' to 'example/Alpha3CountryCode-lf.ddd.svg'"