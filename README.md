# Slinky Framework [![Build Status](https://travis-ci.org/klyall/slinky-framework.svg?branch=master)](https://travis-ci.org/klyall/slinky-framework) [![Code Coverage](https://img.shields.io/codecov/c/github/klyall/slinky-framework/master.svg)](https://codecov.io/github/klyall/slinky-framework?branch=master)

## Introduction

The Slinky Framework is an opinionated framework that makes developing web scale applications quicker and easier.

The framework aims to provide a high level of consistency and removal of duplication across component code and build configurations.

Developers of Slinky applications should therefore not need to be cutting-and-pasting boiler plate code and configuration between components due to the use of configuration builders, aspects and Maven parent POM files. 

## Overview
 
The Slinky Framework is designed around the use of the [Hexagonal Design Pattern](http://alistair.cockburn.us/Hexagonal+architecture) (aka [Ports & Adapters Design Pattern](https://spin.atomicobject.com/2013/02/23/ports-adapters-software-architecture/)).

Using the [Hexagonal Design Pattern](http://alistair.cockburn.us/Hexagonal+architecture) an application has well defined layers. By using well defined interfaces between layers, functionality can be 'hooked' onto or introduced at each layer. 
 
Insert picture here: 

The Slinky Framework uses the RestController, SlinkyApplication, SlinkyClient and SlinkyRepository interfaces and AspectJ aspects to introduce cross-cutting functionality such as [Structured Logging](doc/structured_logging.md).
 
The Slinky Framework also helps manage a component's dependencies by providing a minimal suite of pre-selected dependencies that have had all of their transitive dependencies resolved to avoid conflicts. These conflicts can sometimes be hidden and not encountered until runtime.
 
The packages selected for use when building a Slinky application are:
 - Java 8
 - Maven
 - AspectJ
 - Spring
 - Spring Boot
 - Spring Cloud
 - SLF4J
 - JUnit
 - Hamcrest
 - Mockito
 - Cucumber-JVM
 - WireMock

See [Dependency Management](./doc/dependency_management.md) for how a Slinky application simplifies this problem.

## Features

The following features are provided by the Slinky Framework:
- [Dependency Management](./doc/dependency_management.md)
- [Structured Logging](./doc/structured_logging.md)
- [Environment Builder](./doc/environment_builder.md)


## Build Profiles

To be documented:

mvn clean verify -P coverage
mvn clean compile -P checker
mvn clean install -P docker
mvn clean verify -P performance

