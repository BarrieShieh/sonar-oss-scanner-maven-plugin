
This plugin will scan the code with source code with dependency-check, checkstyle, spotbugs, pmd and generate reports for SonarQube

Embedded scanner
=============

This scanner is embedded with following scanner by default

Embedded Plugin name              |Embedded Plugin name        |Description
----------------------------------|----------------------------|-----------------------------------------------
dependency-check-maven            | 5.3.2                      |                 
maven-checkstyle-plugin           | 3.1.1                      | With Google Java Style
spotbugs-maven-plugin             | 4.0.0                      | With findsecbugs(LATEST), sb-contrib(LATEST)        
maven-pmd-plugin                  | 3.13.0                     | 

Example Usage
=============

How to use this plugin

```shell script
mvn oss:scan
```

SonarQube properties
=============

```xml
<properties>
    <sonar.dependencyCheck.jsonReportPath>${project.build.directory}/dependency-check-report.json</sonar.dependencyCheck.jsonReportPath>
    <sonar.dependencyCheck.htmlReportPath>${project.build.directory}/dependency-check-report.html</sonar.dependencyCheck.htmlReportPath>
    <sonar.dependencyCheck.xmlReportPath>${project.build.directory}/dependency-check-report.xml</sonar.dependencyCheck.xmlReportPath>
    <sonar.java.checkstyle.reportPaths>${project.build.directory}/checkstyle-result.xml</sonar.java.checkstyle.reportPaths>
    <sonar.java.spotbugs.reportPaths>${project.build.directory}/spotbugsXml.xml</sonar.java.spotbugs.reportPaths>
    <sonar.java.pmd.reportPaths>${project.build.directory}/pmd.xml</sonar.java.pmd.reportPaths>
</properties>
```

Maven Dependency
================

NOTE: This plugin is not in the central maven yet. You need to use `mvn install` to add it to local repository.

Add this to your pom.xml:

```xml
<plugin>
    <groupId>org.twdata.maven</groupId>
    <artifactId>sonar-oss-scanner-plugin</artifactId>
    <version>1.0.2</version>
</plugin>
```

You can add more analyzer to the plugins.xml with following format

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <plugins>
        <plugin>
            <goal>${goal}</goal>
            <plugin>
                <groupId>${groupId}</groupId>
                <artifactId>${artifactId}</artifactId>
                <version>${version}</version>
            </plugin>
            <configuration>
                ...
                ${pluginConfiguration}
            </configuration>
        </plugin>
    </plugins>
</configuration>

```

License
=======
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

Contributors
============
[mojo-executor](https://github.com/TimMoore/mojo-executor)

[sonarqube-licensecheck](https://github.com/porscheinformatik/sonarqube-licensecheck)

[checkstyle](https://github.com/checkstyle/checkstyle)

[spotbugs](https://github.com/spotbugs/spotbugs)

[pmd](https://github.com/pmd/pmd)

[mojo-api](http://maven.apache.org/developers/mojo-api-specification.html)
