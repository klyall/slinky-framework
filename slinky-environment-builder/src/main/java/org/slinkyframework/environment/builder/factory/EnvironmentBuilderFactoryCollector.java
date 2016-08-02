package org.slinkyframework.environment.builder.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = {"org.slinkyframework"})
public class EnvironmentBuilderFactoryCollector {

    private static final boolean ALLOW_EAGER_INIT = true;
    private static final boolean INCLUDE_NON_SINGLETONS = true;

    public List<EnvironmentBuilderFactory> findEnvironmentBuilderFactories() {

        ApplicationContext context = new AnnotationConfigApplicationContext(EnvironmentBuilderFactoryCollector.class);

        List<EnvironmentBuilderFactory> factories = new ArrayList<>();

        Map<String, EnvironmentBuilderFactory> beansOfType = context.getBeansOfType(EnvironmentBuilderFactory.class, INCLUDE_NON_SINGLETONS, ALLOW_EAGER_INIT);

        for (String key: beansOfType.keySet()) {
            factories.add(beansOfType.get(key));
        }

        return factories;
    }
}
