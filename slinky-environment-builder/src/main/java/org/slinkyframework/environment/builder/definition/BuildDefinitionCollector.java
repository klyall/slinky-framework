package org.slinkyframework.environment.builder.definition;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@Configuration
@ComponentScan(basePackages = {"environment"})
public class BuildDefinitionCollector {

    private static final boolean ALLOW_EAGER_INIT = true;
    private static final boolean INCLUDE_NON_SINGLETONS = true;

    public Map<Class, Set<BuildDefinition>> findBuildDefinitions() {

        ApplicationContext context = new AnnotationConfigApplicationContext(BuildDefinitionCollector.class);

        Map<Class, Set<BuildDefinition>> buildDefinitions = new HashMap<>();

        Map<String, BuildDefinition> beansOfType = context.getBeansOfType(BuildDefinition.class, INCLUDE_NON_SINGLETONS, ALLOW_EAGER_INIT);

        for (String key: beansOfType.keySet()) {
            addBuildDefintionToMap(buildDefinitions, beansOfType.get(key));
        }

        return buildDefinitions;
    }

    private void addBuildDefintionToMap(Map<Class, Set<BuildDefinition>> buildDefinitions, BuildDefinition buildDefinition) {

        Class clazz = buildDefinition.getClass();

        if (buildDefinitions.containsKey(clazz)) {
            buildDefinitions.get(clazz).add(buildDefinition);
        } else {
            Set<BuildDefinition> specificBuildDefinitions = new TreeSet<>();
            specificBuildDefinitions.add(buildDefinition);

            buildDefinitions.put(clazz, specificBuildDefinitions);
        }
    }
}
