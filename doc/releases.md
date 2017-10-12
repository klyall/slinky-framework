# Slinky Framework Releases

[Home](../README.md) > Releases

## Releases

### v0.1.0

- Basic Structured Logging

### v0.2.1

- Couchbase Repository
- Environment Builder Framework
- Couchbase Environment Builder

### v0.2.2

- Improvements to Couchbase Environment Builder

### v0.2.8
- Resolve build and release issues
- Add Docker tagging and deploying to local or remote registry

### v0.2.9
- Add Full Text Search service to Couchbase setup

### v0.3.0
- Add full text index support for Couchbase
- Upgrade spring-data-couchbase to v2.1.2 for full text search capability
- Upgrade spring-boot to v1.4.0 to support spring-data-couchbase upgrade

### v0.3.1
- Add Spring profile for JSON formatted logging

### v0.3.2
- Add Environment Config Maven Plugin

### v0.3.3
- Add @Loggable annotation for logging of method parameters and return values

### v0.3.4
- Fix: Losing return values from methods wrapped by aspects

### v0.3.5
- Fix: Multi-threading issue in common logging framework

### v0.3.6
- No changes. Just trying to get release into Maven Central.

### v0.3.7
- No changes. Still trying to resolve Maven Central publishing issues.

### v0.3.8
- Fix project build structure stopping publish to Maven Central.
- Exclude slinky-environment-* projects from build as no changes and these should move to a separate project 