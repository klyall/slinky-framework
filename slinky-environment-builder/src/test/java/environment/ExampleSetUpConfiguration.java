package environment;

import org.slinkyframework.environment.builder.definition.BuildDefinition;
import org.slinkyframework.environment.builder.definition.BuildPriority;
import org.slinkyframework.environment.builder.example.AnotherBuildDefinition;
import org.slinkyframework.environment.builder.example.ExampleBuildDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExampleSetUpConfiguration {

    @Bean
    public BuildDefinition exampleBuildDefinition3() {
        return new ExampleBuildDefinition(BuildPriority.NORMAL, "Build3");
    }

    @Bean
    public BuildDefinition exampleBuildDefinition2() {
        return new ExampleBuildDefinition(BuildPriority.NORMAL, "Build2");
    }

    @Bean
    public BuildDefinition exampleBuildDefinition1() {
        return new ExampleBuildDefinition(BuildPriority.HIGH, "First Build");
    }

    @Bean
    public BuildDefinition anotherBuildDefinition1() {
        return new AnotherBuildDefinition(BuildPriority.NORMAL, "First Build", "Extra Data");
    }}
