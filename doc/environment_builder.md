# Slinky Environment Builder

[Home](../README.md) > Environment Builder

## Introduction
A significant problem when developing an automated test suite for an application is having a environment to run the tests against that is setup consistently on all machines and has data in a known, consistent state. 

The ideal scenario is that an application can be checked-out from source control (e.g. GIT, SVN) and successfully built using `mvn clean verify`. For this to work consistently a project build needs to setup it's environment to a known state and create it's own test data.  

To solve this problem the Slinky Framework provides an Environment Builder capability. 

The Environment Builder supports the setup and teardown of the following technologies:

- Couchbase

> Note: The Slinky Environment Builder is a standalone capability and can be used without using the other Slinky Framework capabilities. 

The Environment Builder consists of a Maven build plugin that hooks into the `pre-integration-test` and `post-integration-test` phases of Maven projects. The build plugin picks up all environment build definitions and executes them to setup and tear down the application's environment as required.

## Getting Started

The first thing that needs to be done is to create one or more build definitions for the environment and then configure the Maven build plugin.

### Defining Environment Build

A component defines it environment build in a separate child Maven project (movie-service-setup) in the component's multi-module project. For instance:

```
movie-service-parent
├── movie-service
├── movie-service-api
├── movie-service-performance-tests
├── movie-service-setup
```

The project simply contains build definitions for each infrastructure item that is specific to that component. 

If each layer of the application architecture is encapsulated in a separate multi-module project, then each layer should define it's own environment builders and then chain them together using Maven dependencies. For example:  
 
```
movie-service-parent
├── movie-service
├── movie-service-api
├── movie-service-performance-tests
├── movie-service-setup
├─────depends on movie-repository-setup
```


A build definition is defined by creating a Spring Configuration class in the environment package as below.

> NOTE: The configuration class must be in the environment package to be picked up by classpath scanning.

```
package environment;

import org.slinkyframework.environment.builder.couchbase.CouchbaseBuildDefinition;
import org.slinkyframework.environment.builder.definition.BuildDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExampleEnvironmentBuilderConfiguration {

    private static final String DESCRIPTION = "Couchbase Movies Bucket";
    private static final String BUCKET_NAME = "movies";
    private static final String BUCKET_PASSWORD = "strong_password";
    private static final String DOCUMENT_PACKAGE = "org.example.movie.repository.domain";
    private static final String DOCUMENT_CLASS_NAME = "MovieDocument";

    @Bean
    public BuildDefinition movieCouchbaseBuildDefinition() {
        CouchbaseBuildDefinition buildDefinition = new CouchbaseBuildDefinition(DESCRIPTION, BUCKET_NAME, DOCUMENT_PACKAGE, DOCUMENT_CLASS_NAME);
        buildDefinition.setBucketPassword(BUCKET_PASSWORD);

        buildDefinition.addSpatialView("spatial", "views/spatial.js");

        return buildDefinition;
    }
}
```

Each type of environment builder will have a different build definition class to use.

Multiple build definitions can be defined in a single configuration class or across multiple configuration classes.

### Maven Build Plugin

The Environment Builder is included into the Maven build process by adding `slinky-environment-builder-maven-plugin` as a build dependency.
 
This dependency should be added to each project that needs to setup and tear down it's environment e.g. the main `movie-service` and the `movie-service-acceptance-tests`.

The Maven configuration to add is:

```
    <build>
        <plugins>
            <plugin>
                <groupId>org.slinkyframework</groupId>
                <artifactId>slinky-environment-builder-maven-plugin</artifactId>
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

> Make sure to add a dependency on your project's setup project.

Every time the Maven `integration-test` phase is executed the environment will be setup and torn down.

The build plugin can be configured by passing in the following parameters:

| Parameter        | Default Value | Description |
|------------------|---------------|-------------|
| env.host         | localhost     | The hostname to build the environment on. |
| env.docker       | false         | Flag to indicate whether Docker will be used for build environment. |
| env.skipSetup    | false         | Flag whether to skip setup of build environment. Typically used on developer workstations to speed up builds when environment is fairly static. |
| env.skipTearDown | false         | Flag whether to skip tear down of build environment. Typically used on developer workstations to speed up builds when environment is fairly static |

For example:

```
mvn clean verify -Denv.docker=true -Denv.host=dev
```

> Where Docker is being used, it is assumed that the following environment variables have been defined:
>
> - DOCKER_TLS_VERIFY
> - DOCKER_HOST
> - DOCKER_CERT_PATH
> - DOCKER_MACHINE_NAME