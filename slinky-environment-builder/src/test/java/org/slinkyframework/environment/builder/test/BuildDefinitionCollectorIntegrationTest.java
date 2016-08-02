package org.slinkyframework.environment.builder.test;

import org.junit.Before;
import org.junit.Test;
import org.slinkyframework.environment.builder.definition.BuildDefinition;
import org.slinkyframework.environment.builder.definition.BuildDefinitionCollector;
import org.slinkyframework.environment.builder.definition.BuildPriority;
import org.slinkyframework.environment.builder.example.AnotherBuildDefinition;
import org.slinkyframework.environment.builder.example.ExampleBuildDefinition;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class BuildDefinitionCollectorIntegrationTest {

    private BuildDefinition definition1 = new ExampleBuildDefinition(BuildPriority.HIGH, "First Build");
    private BuildDefinition definition2 = new ExampleBuildDefinition(BuildPriority.NORMAL, "Build2");
    private BuildDefinition definition3 = new ExampleBuildDefinition(BuildPriority.NORMAL, "Build3");
    private BuildDefinition definition4 = new AnotherBuildDefinition(BuildPriority.NORMAL, "Build4", "More  config");

    private BuildDefinitionCollector testee;

    @Before
    public void setUp() {
        testee = new BuildDefinitionCollector();
    }

    @Test
    public void shouldFindBuildDefinitionsByType() {

        Map<Class, Set<BuildDefinition>> buildDefinitions = testee.findBuildDefinitions();

        assertThat("BuildDefinitions", buildDefinitions.keySet(), hasSize(2));
        assertThat("BuildDefinitions", buildDefinitions, hasKey(equalTo(definition1.getClass())));
        assertThat("BuildDefinitions", buildDefinitions, hasKey(equalTo(definition4.getClass())));
    }

    @Test
    public void shouldOrderBuildDefinitionsByOrderByName() {

        Map<Class, Set<BuildDefinition>> buildDefinitions = testee.findBuildDefinitions();

        Set<BuildDefinition> exampleBuildDefinitions = buildDefinitions.get(definition1.getClass());
        assertThat("BuildDefinitions", exampleBuildDefinitions, contains(definition1, definition2, definition3));
    }
}
