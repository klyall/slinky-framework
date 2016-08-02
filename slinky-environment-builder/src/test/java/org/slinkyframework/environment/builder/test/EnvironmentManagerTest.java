package org.slinkyframework.environment.builder.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slinkyframework.environment.builder.definition.BuildDefinition;
import org.slinkyframework.environment.builder.definition.BuildDefinitionCollector;
import org.slinkyframework.environment.builder.definition.BuildPriority;
import org.slinkyframework.environment.builder.EnvironmentBuilder;
import org.slinkyframework.environment.builder.EnvironmentBuilderContext;
import org.slinkyframework.environment.builder.factory.EnvironmentBuilderFactory;
import org.slinkyframework.environment.builder.factory.EnvironmentBuilderFactoryCollector;
import org.slinkyframework.environment.builder.EnvironmentManager;
import org.slinkyframework.environment.builder.EnvironmentManagerImpl;
import org.slinkyframework.environment.builder.example.AnotherBuildDefinition;
import org.slinkyframework.environment.builder.example.ExampleBuildDefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnvironmentManagerTest {

    private static final String TEST_TARGET_HOST = "localhost";
    private static final boolean TEST_USE_DOCKER = false;

    @Mock BuildDefinitionCollector mockBuildDefinitionCollector;
    @Mock EnvironmentBuilderFactoryCollector mockEnvironmentBuilderFactoryCollector;
    @Mock EnvironmentBuilderFactory mockExampleEnvironmentBuilderFactory;
    @Mock EnvironmentBuilder mockExampleEnvironmentBuilder;
    @Mock EnvironmentBuilder mockAnotherEnvironmentBuilder;

    private EnvironmentManager testee;
    private EnvironmentBuilderContext environmentBuilderContext;

    private Map<Class, Set<BuildDefinition>> testDefinitions;
    private Set<BuildDefinition> anotherBuildDefinitions;
    private Set<BuildDefinition> exampleBuildDefinitions;
    private BuildDefinition exampleDefinition = new ExampleBuildDefinition(BuildPriority.HIGH, "Build1");
    private BuildDefinition anotherDefinition = new AnotherBuildDefinition(BuildPriority.NORMAL, "Build2", "More  config");

    @Before
    public void setUp() {
        environmentBuilderContext = new EnvironmentBuilderContext(TEST_TARGET_HOST, TEST_USE_DOCKER);

        testDefinitions = new HashMap<>();

        exampleBuildDefinitions = new TreeSet<>();
        exampleBuildDefinitions.add(exampleDefinition);

        anotherBuildDefinitions = new TreeSet<>();
        anotherBuildDefinitions.add(anotherDefinition);

        testDefinitions.put(exampleDefinition.getClass(), exampleBuildDefinitions);
        testDefinitions.put(anotherDefinition.getClass(), anotherBuildDefinitions);

        when(mockBuildDefinitionCollector.findBuildDefinitions()).thenReturn(testDefinitions);

        when(mockExampleEnvironmentBuilderFactory.forClass(ExampleBuildDefinition.class)).thenReturn(true);
        when(mockExampleEnvironmentBuilderFactory.forClass(AnotherBuildDefinition.class)).thenReturn(false);
        when(mockExampleEnvironmentBuilderFactory.getInstance(environmentBuilderContext)).thenReturn(mockExampleEnvironmentBuilder);

        List<EnvironmentBuilderFactory> testEnvironmentBuilderFactories = new ArrayList<>();
        testEnvironmentBuilderFactories.add(mockExampleEnvironmentBuilderFactory);
        when(mockEnvironmentBuilderFactoryCollector.findEnvironmentBuilderFactories()).thenReturn(testEnvironmentBuilderFactories);

        testee = new EnvironmentManagerImpl(mockBuildDefinitionCollector, mockEnvironmentBuilderFactoryCollector);
    }

    @Test
    public void inSetUpShouldFindAvailableBuildDefinitions() {

        testee.setUp(environmentBuilderContext);

        verify(mockEnvironmentBuilderFactoryCollector).findEnvironmentBuilderFactories();
    }

    @Test
    public void inSetUpShouldFindAvailableEnvironmentBuilderFactories() {

        testee.setUp(environmentBuilderContext);

        verify(mockBuildDefinitionCollector).findBuildDefinitions();
    }

    @Test
    public void inSetUpShouldFindEnvironmentBuilderForEachBuildDefinitionType() {

        testee.setUp(environmentBuilderContext);

        verify(mockExampleEnvironmentBuilderFactory).getInstance(environmentBuilderContext);
    }

    @Test
    public void inSetUpShouldCallEnvironmentBuilderForEachBuildDefinitionType() {

        testee.setUp(environmentBuilderContext);

        verify(mockExampleEnvironmentBuilder).setUp(exampleBuildDefinitions);
    }

    @Test
    public void inTearDownShouldFindAvailableBuildDefinitionsInTearDown() {

        testee.tearDown(environmentBuilderContext);

        verify(mockBuildDefinitionCollector).findBuildDefinitions();
    }


    @Test
    public void inTearDownShouldFindAvailableEnvironmentBuilderFactories() {

        testee.tearDown(environmentBuilderContext);

        verify(mockBuildDefinitionCollector).findBuildDefinitions();
    }

    @Test
    public void inTearDownShouldFindEnvironmentBuilderForEachBuildDefinitionType() {

        testee.tearDown(environmentBuilderContext);

        verify(mockExampleEnvironmentBuilderFactory).getInstance(environmentBuilderContext);
    }

    @Test
    public void inTearDownShouldCallEnvironmentBuilderForEachBuildDefinitionType() {

        testee.tearDown(environmentBuilderContext);

        verify(mockExampleEnvironmentBuilder).tearDown(exampleBuildDefinitions);
    }
}
