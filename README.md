
This plugin will scan the code with source code with dependency-check, checkstyle, spotbugs, pmd and generate reports for SonarQube

Example Usage
=============

How to use this plugin

``` bash
mvn oss:scan
```

Maven Dependency
================

Add this to your pom.xml:

``` xml
<plugin>
    <groupId>org.twdata.maven</groupId>
    <artifactId>oss</artifactId>
    <version>1.0.1</version>
</plugin>
```

There are a few versions available, and the best one to use will depend on the version(s) of Maven you need to support:

  - 1.0.1 &mdash; Supports Maven 2.x only
  - 1.5   &mdash; Supports both Maven 2.x and Maven 3.x
  - 2.0.x &mdash; Supports Maven 3.0.x only
  - 2.1.x &mdash; Supports Maven 3.0.x and 3.1.x

License
=======
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

Contributors
============
[mojo-executor]:https://github.com/TimMoore/mojo-executor


[sonarqube-licensecheck]: https://github.com/porscheinformatik/sonarqube-licensecheck
[checkstyle]:https://github.com/checkstyle/checkstyle
[spotbugs]:https://github.com/spotbugs/spotbugs
[pmd]:https://github.com/pmd/pmd


[mojo-api]: http://maven.apache.org/developers/mojo-api-specification.html
