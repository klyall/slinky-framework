package org.slinkyframework.environment.builder.factory;

import org.slinkyframework.environment.builder.EnvironmentBuilder;
import org.slinkyframework.environment.builder.EnvironmentBuilderContext;

public interface EnvironmentBuilderFactory {
    boolean forClass(Class buildDefinitionClass);
    EnvironmentBuilder getInstance(EnvironmentBuilderContext environmentBuilderContext);
}
