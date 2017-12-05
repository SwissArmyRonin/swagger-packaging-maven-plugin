# Swagger definition packaging

This plugin allows developers to treat Swagger YAML files as Maven modules. Swagger files are installed in the local repository and can be deployed to remote repositories.

<!--
To use, make sure you add the Sonatype repository to either your settings or your POM:

```xml
<pluginRepositories>
	<pluginRepository>
		<snapshots>
			<enabled>false</enabled>
		</snapshots>
		<id>central</id>
		<name>sonatype-plugins-release</name>
		<url>https://oss.sonatype.org/content/repositories/releases</url>
	</pluginRepository>
	<pluginRepository>
		<snapshots />
		<id>snapshots</id>
		<name>sonatype-plugins-snapshot</name>
		<url>https://oss.sonatype.org/content/repositories/snapshots</url>
	</pluginRepository>
</pluginRepositories>
```
-->

## Usage

To create a Swagger definition module, create a project containing the Swagger YAML definition with a POM file like the one in this example:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>dk.swissarmyronin.services</groupId>
  <artifactId>example-service</artifactId>
  <version>1.0</version>
  <packaging>swagger-yaml</packaging>
  <build>
    <plugins>
      <plugin>
        <groupId>dk.swissarmyronin</groupId>
        <artifactId>swagger-packaging-maven-plugin</artifactId>
        <version>1.1</version>
        <extensions>true</extensions>
        <configuration>
          <sourceDir>${basedir}/src/main/resources</sourceDir> <!-- Optional: defaults to ${project.basedir} -->
          <swaggerFile>example.yaml</swaggerFile> <!-- Optional: defaults to "${project.artifactId}.yaml" -->
        </configuration>
      </plugin>
    </plugins>
  </build>
</project>
```

Packaging should be "swagger-yaml" for YAML files, and "swagger-json" for JSON files.

To use the file for code generation in another project, insert the following plugin snippets in that project's POM file:

```xml
...
<plugins>
  <plugin>
    <artifactId>maven-dependency-plugin</artifactId>
    <version>3.0.2</version>
    <executions>
      <execution>
        <id>copy-swagger</id>
        <phase>initialize</phase>
        <goals>
          <goal>copy</goal>
        </goals>
        <configuration>
          <artifactItems>
            <artifactItem>
              <groupId>dk.tmnet.tmt</groupId>
              <artifactId>example-service</artifactId>
              <version>1.0</version>
              <type>yaml</type>
              <outputDirectory>${project.basedir}/src/main/resources</outputDirectory>
              <destFileName>example-service.yaml</destFileName>
            </artifactItem>
          </artifactItems>
          <overWriteSnapshots>true</overWriteSnapshots>
        </configuration>
      </execution>
    </executions>
  </plugin>
  <plugin>
    <groupId>io.swagger</groupId>
    <artifactId>swagger-codegen-maven-plugin</artifactId>
    <version>2.2.3</version>
    <executions>
      <execution>
        <id>generate-swagger</id>
        <goals>
          <goal>generate</goal>
        </goals>
        <configuration>
          <inputSpec>${project.basedir}/src/main/resources/example-service.yaml</inputSpec>
          <language>jaxrs</language>
          <output>${project.build.directory}/generated-sources/swagger</output>
        </configuration>
      </execution>
    </executions>
  </plugin>
...
```

If you have other projects, say Angular or .NET projects that need the Swagger file, they can download it at build time with the following command line (provided they have Maven installed):

```
mvn dependency:copy -Dartifact=dk.swissarmyronin.services:example-service:1.0:yaml -DoutputDirectory=.
```

... outputs "example-service-1.0.yaml" to current directory.
