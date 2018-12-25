# code2svg-m2-plugin
Maven plugin that converts code into SVG text with syntax highlighting.

## Usage
Add the following plugin to your Maven pom.xml:
```xml
<plugin>
    <groupId>org.fuin.code2svg</groupId>
    <artifactId>code2svg-m2-plugin</artifactId>
    <version>0.1.0-SNAPSHOT</version>
    <executions>
        <execution>
            <goals>
                <goal>convert</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <!-- Path and name of the configuration file. Defaults to 'code-2-svg.xml' in the current directory -->
        <config-file>src/test/resources/code-2-svg.xml</config-file>
        <!-- Path and name of the target directory for the generated SVG files. Defaults to 'src/main/resources' -->
        <target-dir>target/svg</target-dir>
        <!-- List of files or directories to convert. Defaults to 'src/main/resources' -->
        <source-files-dirs>
            <argument>src/test/resources</argument>
        </source-files-dirs>
    </configuration>
</plugin>
```

If the source file is a directory, the 'file-extension' in the configuration file will be used to locate source files recursively.

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<code2svg file-extension=".ddd">
...
</code2svg>
```

If you just want to generate the SVG you can use the following command:
```
mvn -o org.fuin.code2svg:code2svg-m2-plugin:0.1.0-SNAPSHOT:convert
```
(The "-o" enables the "offline" mode and avoid loading dependencies if you've already built successfully once before). 


