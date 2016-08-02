package org.slinkyframework.environment.builder.example;

import org.slinkyframework.environment.builder.definition.AbstractBuildDefinition;
import org.slinkyframework.environment.builder.definition.BuildPriority;

public class ExampleBuildDefinition extends AbstractBuildDefinition {

    public ExampleBuildDefinition(BuildPriority buildPriority, String name) {
        super(buildPriority, name);
    }

//    @Override
//    public boolean equals(Object o) {
//        return EqualsBuilder.reflectionEquals(this, o);
//    }
//
//    @Override
//    public int hashCode() {
//        return HashCodeBuilder.reflectionHashCode(this);
//    }
}
