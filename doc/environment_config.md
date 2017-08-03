# Slinky Environment Config Maven Plugin

[Home](../README.md) > Environment Config

## Introduction
Managing the configuration of multiple applications or services across many environments is never an easy task. 

One situation to be avoided is having to maintain permutations of properties for every application/service and environment.

An easier approach is to have a hierarchy of properties. This allows common properties to be specified once at a higher level and be used across multiple applications/services or environments. 

Properties can also be specified at a higher level and then overriden at a lower level. For instance specifying a default connection pool size to be used across all environment and then overriding it with a tuned value for the NFT and production environments. 

The Slinky Environment Config uses a Maven Plugin to merge the hierarchy of property files to then generate config files for each application / environment combination. Where files are static, i.e. not a template , they can also be copied to each application and environment.

The Slinky Environment Config Maven Plugin integrates with the Maven default lifecycle at the following phases:
*  `generate-resources`  - to generate and copy the configuration files from the merged property files
 *  `package` - to zip up the generated/copied configuration files into a application/environment specific version Zip file
 *  `install` - to install the version Zip file artifacts in the local Maven repository
 *  `deploy` - to deploy the versioned Zip file artifacts in the centralised Maven repository

### Configuration Hierarchy
The Slinky Environment Config Maven Plugin uses property files, templates and static files defined at one or more of the following levels:

```
─── global
    └── application
        └── environment
            └── application-environment
```


Using this hierarchy:
- Properties defined at a higher level can be overriden at a lower level
- Properties defined at the global level are available to all templates for all applications in all environments.  
- Properties defined at the application level are available to all templates for the particular application in all environments.  
- Properties defined at the environment level are available to all templates for all applications in the particular environments.  
- Properties can defaulted for all environments using the application or global level and then overriden for the prod environment

## Getting Started

Add the following to the build section of your Maven pom.xml:

```
    <build>
        <pluginManagement>
            <plugins>
                <plugin>
                    <groupId>org.slinkyframework</groupId>
                    <artifactId>slinky-environment-config-maven-plugin</artifactId>
                    <version>${slinky-environment-config-maven-plugin.version}</version>
                    <configuration>
                        <delimiters>
                            <delimiter>${*}</delimiter>
                            <delimiter>{{*}}</delimiter>
                        </delimiters>
                    </configuration>
                </plugin>
            </plugins>
        </pluginManagement>
        <plugins>
            <plugin>
                <groupId>org.slinkyframework</groupId>
                <artifactId>slinky-environment-config-maven-plugin</artifactId>
                <executions>
                    <execution>
                        <id>merge</id>
                        <goals>
                            <goal>merge</goal>
                        </goals>
                        <phase>generate-resources</phase>
                    </execution>
                    <execution>
                        <id>package</id>
                        <goals>
                            <goal>package</goal>
                        </goals>
                        <phase>package</phase>
                    </execution>
                    <execution>
                        <id>install</id>
                        <goals>
                            <goal>install</goal>
                        </goals>
                        <phase>install</phase>
                    </execution>
                    <execution>
                        <id>deploy</id>
                        <goals>
                            <goal>deploy</goal>
                        </goals>
                        <phase>deploy</phase>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```

In your project, create a folder structure similar to the following in the `src/main/resources` folder using your application and environment names:


```
├── applications
│   ├── app1
│   │   ├── application.properties
│   │   ├── files
│   │   └── templates
│   └── app2
├── environments
│   ├── env1
│   │   ├── applications
│   │   │   └── app1
│   │   │       ├── application.properties
│   │   │       ├── files
│   │   │       └── templates
│   │   ├── environment.properties
│   │   ├── files
│   │   └── templates
│   └── env2
│       └── environment.properties
└── global
    ├── files
    ├── global.properties
    └── templates
```

Now when you run 
- `mvn package` -  the config will be generated and compressed into Zip files `target/generated-config/<environment_name_>/<application_name>-config-<version>.zip`
- `mvn install` - the config Zip files will be installed into the local Maven repository
- `mvn deploy` - the config Zip files will be deployed to the shared Maven repository
 
## Slinky Environment Config Maven Plugin Goals

Using the above configuration in your pom.xml you will not need to call the Slinky Environment Config Maven Plugin goals directly but should you want to here they are:

- `mvn config:merge` - merges the property files and generates the files into `target/generated-config`
- `mvn config:package` - creates the config Zip files
- `mvn config:install` - installs the config Zip files into the local Maven repository
- `mvn config:deploy` - deploys the config Zip files to the shared Maven repository

### ... and one last thing

One additional goal that is very useful is:

`mvn config:info` 

This will list all of your configuration alphabetically in the order that it gets loaded i.e. global, application, environment and then application/environment. 

This allows you to see where properties can be pushed up the hierarchy or where properties are getting override.