package org.slinkyframework.environment.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slinkyframework.environment.builder.definition.BuildDefinition;
import org.slinkyframework.environment.builder.definition.BuildDefinitionCollector;
import org.slinkyframework.environment.builder.factory.EnvironmentBuilderFactory;
import org.slinkyframework.environment.builder.factory.EnvironmentBuilderFactoryCollector;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class EnvironmentManagerImpl implements EnvironmentManager {

    private static final Logger LOG = LoggerFactory.getLogger(EnvironmentManagerImpl.class);

    private BuildDefinitionCollector buildDefinitionCollector;
    private EnvironmentBuilderFactoryCollector environmentBuilderFactoryCollector;
    private Map<Class, Set<BuildDefinition>> buildDefinitions;
    private List<EnvironmentBuilderFactory> environmentBuilderFactories;

    public EnvironmentManagerImpl() {
        this(new BuildDefinitionCollector(), new EnvironmentBuilderFactoryCollector());
    }

    public EnvironmentManagerImpl(BuildDefinitionCollector buildDefinitionCollector, EnvironmentBuilderFactoryCollector environmentBuilderFactoryCollector) {
        this.buildDefinitionCollector = buildDefinitionCollector;
        this.environmentBuilderFactoryCollector = environmentBuilderFactoryCollector;

        buildDefinitions = buildDefinitionCollector.findBuildDefinitions();
        environmentBuilderFactories = environmentBuilderFactoryCollector.findEnvironmentBuilderFactories();
    }

    @Override
    public void setUp(EnvironmentBuilderContext context) {
        buildDefinitions.forEach((buildDefinitionClass, buildDefinitions) -> setUpEnvironment(context, buildDefinitionClass, buildDefinitions));
    }

    private void setUpEnvironment(EnvironmentBuilderContext context, Class buildDefinitionClass, Set<BuildDefinition> buildDefinitions) {
        Optional<EnvironmentBuilder> environmentBuilder = findEnvironmentBuilder(context, buildDefinitionClass);
        if (environmentBuilder.isPresent()) {
            environmentBuilder.get().setUp(buildDefinitions);
        }
    }

    @Override
    public void tearDown(EnvironmentBuilderContext context) {
        buildDefinitions.forEach((buildDefinitionClass, buildDefinitions) -> tearDownEnvironment(context, buildDefinitionClass, buildDefinitions));
    }

    private void tearDownEnvironment(EnvironmentBuilderContext context, Class buildDefinitionClass, Set<BuildDefinition> buildDefinitions) {
        Optional<EnvironmentBuilder> environmentBuilder = findEnvironmentBuilder(context, buildDefinitionClass);
        if (environmentBuilder.isPresent()) {
            environmentBuilder.get().tearDown(buildDefinitions);
        }
    }

    private Optional<EnvironmentBuilder> findEnvironmentBuilder(EnvironmentBuilderContext context, Class buildDefinitionClass) {
        for (EnvironmentBuilderFactory factory: environmentBuilderFactories) {
            if (factory.forClass(buildDefinitionClass)) {
                return Optional.of(factory.getInstance(context));
            }
        }
        LOG.error("No EnvironmentBuilderFactory for build definitions of type '{}'", buildDefinitionClass.getSimpleName());
        return Optional.empty();
    }
}
