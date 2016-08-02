package org.slinkyframework.environment.builder.example;

import org.slinkyframework.environment.builder.definition.AbstractBuildDefinition;
import org.slinkyframework.environment.builder.definition.BuildPriority;

public class AnotherBuildDefinition extends AbstractBuildDefinition {

    private final String extraData;

    public AnotherBuildDefinition(BuildPriority buildPriority, String name, String extraData) {
        super(buildPriority, name);
        this.extraData = extraData;
    }
}
