# Slinky Environment Builder

[Home](../README.md)

## Introduction

| Parameter          | Default Value | Description |
|--------------------|---------------|-------------|
| env.host           | localhost     | The hostname to build the environment on. |
| env.docker         | false         | Flag to indicate whether Docker will be used for build environment. |
| env.skipSetup      | false         | Flag whether to skip setup of build environment. Typically used on developer workstations to speed up builds when environment is fairly static. |
| env.isSkipTearDown | false         | Flag whether to skip tear down of build environment. Typically used on developer workstations to speed up builds when environment is fairly static |



## Getting Started

### Define Environment Build Definition

> NOTE: The configuration class must be in the environment package to be picked up by classpath scanning.

```
package environment;

import org.slinkyframework.environment.builder.couchbase.CouchbaseBuildDefinition;
import org.slinkyframework.environment.builder.definition.BuildDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExampleEnvironmentBuilderConfiguration {

    private static final String BUCKET_NAME = "movies";
    private static final String BUCKET_PASSWORD = "strong_password";
    private static final String DOCUMENT_PACKAGE = "org.example.movie.repository.domain";
    private static final String DOCUMENT_CLASS_NAME = "BranchDocument";

    @Bean
    public BuildDefinition movieCouchbaseBuildDefinition() {
        CouchbaseBuildDefinition buildDefinition = new CouchbaseBuildDefinition("Branch Couchbase Database", "dev", BUCKET_NAME, DOCUMENT_PACKAGE, DOCUMENT_CLASS_NAME);
        buildDefinition.setBucketPassword(BUCKET_PASSWORD);

        buildDefinition.addView(SpatialView.create(
                "by_location"
                , "function (doc, meta) { if (doc.type == 'org.example.movie.repository.domain.MovieDocument') { emit([doc.geo.longitude, doc.geo.latitude], null); } }")
        );

        return buildDefinition;
    }
}
```



### Maven Build Plugin

```
    <build>
        <plugins>
            <plugin>
                <groupId>org.slinkyframework</groupId>
                <artifactId>slinky-environment-builder-maven-plugin</artifactId>
                <version>${slinky-framework.version}</version>
                <executions>
                    <execution>
                        <id>env.setup</id>
                        <goals>
                            <goal>setup</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>env.teardown</id>
                        <goals>
                            <goal>teardown</goal>
                        </goals>
                    </execution>
                </executions>
                <dependencies>
                    <dependency>
                        <groupId>org.example.services</groupId>
                        <artifactId>example-service-setup</artifactId>
                        <version>${project.version}</version>
                    </dependency>
                </dependencies>
            </plugin>
        </plugins>
    </build>
```
