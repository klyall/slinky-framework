package org.slinkyframework.environment.builder.example;

import org.slinkyframework.environment.builder.EnvironmentBuilder;
import org.slinkyframework.environment.builder.EnvironmentBuilderContext;
import org.slinkyframework.environment.builder.factory.EnvironmentBuilderFactory;
import org.springframework.stereotype.Component;

@Component
public class ExampleEnvironmentBuilderFactory implements EnvironmentBuilderFactory {

    @Override
    public boolean forClass(Class buildDefinitionClass) {
        return buildDefinitionClass.equals(ExampleBuildDefinition.class);
    }

    @Override
    public EnvironmentBuilder getInstance(EnvironmentBuilderContext environmentBuilderContext) {
        return null;
    }
}
