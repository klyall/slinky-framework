package org.slinkyframework.environment.builder;

import org.slinkyframework.environment.builder.definition.BuildDefinition;

import java.util.Set;

public interface EnvironmentBuilder<T extends BuildDefinition> {
    void setUp(Set<T> buildDefinitions);
    void tearDown(Set<T> buildDefinitions);
    void cleanUp();
}
