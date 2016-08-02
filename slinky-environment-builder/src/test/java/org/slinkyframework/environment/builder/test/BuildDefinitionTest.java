package org.slinkyframework.environment.builder.test;

import org.junit.Test;
import org.slinkyframework.environment.builder.definition.BuildDefinition;
import org.slinkyframework.environment.builder.definition.BuildPriority;
import org.slinkyframework.environment.builder.example.ExampleBuildDefinition;

import java.util.Set;
import java.util.TreeSet;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BuildDefinitionTest {

    private static final String TEST_NAME = "Test";

    @Test
    public void shouldBeEqual() {
        ExampleBuildDefinition definition1 = new ExampleBuildDefinition(BuildPriority.LOW, TEST_NAME);
        ExampleBuildDefinition definition2 = new ExampleBuildDefinition(BuildPriority.LOW, TEST_NAME);

        assertThat("BuildDefinition", definition1, is(equalTo(definition2)));
    }

    @Test
    public void shouldBeOrderByPriority() {

        ExampleBuildDefinition definition1 = new ExampleBuildDefinition(BuildPriority.LOW, TEST_NAME);
        ExampleBuildDefinition definition2 = new ExampleBuildDefinition(BuildPriority.NORMAL, TEST_NAME);
        ExampleBuildDefinition definition3 = new ExampleBuildDefinition(BuildPriority.HIGH, TEST_NAME);

        Set<BuildDefinition> buildDefinitions = new TreeSet<>();

        buildDefinitions.add(definition1);
        buildDefinitions.add(definition2);
        buildDefinitions.add(definition3);

        assertThat("BuildDefinitions", buildDefinitions, contains(definition3, definition2, definition1));
    }

    @Test
    public void shouldBeOrderByName() {

        ExampleBuildDefinition definition1 = new ExampleBuildDefinition(BuildPriority.NORMAL, "B");
        ExampleBuildDefinition definition2 = new ExampleBuildDefinition(BuildPriority.NORMAL, "A");

        Set<BuildDefinition> buildDefinitions = new TreeSet<>();

        buildDefinitions.add(definition1);
        buildDefinitions.add(definition2);

        assertThat("BuildDefinitions", buildDefinitions, contains(definition2, definition1));
    }

    @Test
    public void shouldBeOrderByPriorityThenByName() {

        ExampleBuildDefinition definition1 = new ExampleBuildDefinition(BuildPriority.NORMAL, "B");
        ExampleBuildDefinition definition2 = new ExampleBuildDefinition(BuildPriority.NORMAL, "A");
        ExampleBuildDefinition definition3 = new ExampleBuildDefinition(BuildPriority.HIGH, "B");
        ExampleBuildDefinition definition4 = new ExampleBuildDefinition(BuildPriority.HIGH, "A");

        Set<BuildDefinition> buildDefinitions = new TreeSet<>();

        buildDefinitions.add(definition1);
        buildDefinitions.add(definition2);
        buildDefinitions.add(definition3);
        buildDefinitions.add(definition4);

        assertThat("BuildDefinitions", buildDefinitions, contains(definition4, definition3, definition2, definition1));
    }
}
