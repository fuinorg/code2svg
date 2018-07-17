# code2svg-app
Java command line application that converts source files into SVG files with syntax highlighting.

## Usage

### Linux
Download and unarchive the file to your disk: 

https://fuinorg.jfrog.io/fuinorg/files/code2svg-app/code2svg-app-0.1.0-SNAPSHOT-bin.tar.gz

Execute the shell script to start the conversion:
```
./code2svg.sh CONFIG_NAME FILE_OR_DIR_1 ... FILE_OR_DIR_N
```

Examples:
```
./code2svg.sh example/code-2-svg.xml example/Alpha3CountryCode-lf.ddd
./code2svg.sh example/code-2-svg.xml example/
```

**Tip:** You can just execute the file ``./code2svg-example.sh`` without any arguments to run the above sample.


### Windows
Download and unzip the file to your disk: 

https://fuinorg.jfrog.io/fuinorg/files/code2svg-app/code2svg-app-0.1.0-SNAPSHOT-bin.zip

Execute the batch file to start the conversion:
```
code2svg.bat CONFIG_NAME FILE_OR_DIR_1 ... FILE_OR_DIR_N
```

Examples:
```
code2svg.bat example/code-2-svg.xml example/Alpha3CountryCode-crlf.ddd
code2svg.bat example/code-2-svg.xml example/
```

**Tip:** You can just execute the file ``code2svg-example.bat`` without any arguments to run the above sample.

## Limitations
* The line endings of the source files must be the same as for the platform you run the converter. What does this mean? Converting a source file that has Linux (LF) line endings on a Windows system will not be displayed correctly in the Browser. There will be only a single (very) long line. 


