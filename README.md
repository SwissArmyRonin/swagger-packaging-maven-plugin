# Swagger definition packaging

##Usage

POM example defining a service artifact:
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>dk.swissarmyronin.services</groupId>
  <artifactId>example-service</artifactId>
  <version>1.0</version>
  <packaging>swagger</packaging>
  <build>
    <plugins>
      <plugin>
        <groupId>dk.swissarmyronin</groupId>
        <artifactId>swagger-packaging-maven-plugin</artifactId>
        <version>1.0</version>
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

POM example of project using the Swagger artifact:
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

CLI example:
```bash
mvn dependency:copy -Dartifact=dk.swissarmyronin.services:example-service:1.0:yaml -DoutputDirectory=.
```

... outputs "example-service-1.0.yaml" to current directory.