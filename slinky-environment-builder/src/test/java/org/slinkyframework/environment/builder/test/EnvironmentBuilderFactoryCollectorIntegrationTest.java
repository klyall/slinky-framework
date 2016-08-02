package org.slinkyframework.environment.builder.test;

import org.junit.Before;
import org.junit.Test;
import org.slinkyframework.environment.builder.factory.EnvironmentBuilderFactory;
import org.slinkyframework.environment.builder.factory.EnvironmentBuilderFactoryCollector;
import org.slinkyframework.environment.builder.example.ExampleEnvironmentBuilderFactory;

import java.util.List;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

public class EnvironmentBuilderFactoryCollectorIntegrationTest {

    private EnvironmentBuilderFactoryCollector testee;
    private ExampleEnvironmentBuilderFactory exampleEnvironmentBuilderFactory;

    @Before
    public void setUp() {
        testee = new EnvironmentBuilderFactoryCollector();

        exampleEnvironmentBuilderFactory = new ExampleEnvironmentBuilderFactory();
    }


    @Test
    public void shouldFindEnvironmentBuilderFactories() {
        List<EnvironmentBuilderFactory> factories = testee.findEnvironmentBuilderFactories();

        assertThat("Environment Builder Factories", factories, contains(any(EnvironmentBuilderFactory.class)));
    }
}
