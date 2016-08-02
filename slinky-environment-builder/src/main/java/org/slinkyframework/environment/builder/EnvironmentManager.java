package org.slinkyframework.environment.builder;

public interface EnvironmentManager {
    void setUp(EnvironmentBuilderContext context);
    void tearDown(EnvironmentBuilderContext context);
}
