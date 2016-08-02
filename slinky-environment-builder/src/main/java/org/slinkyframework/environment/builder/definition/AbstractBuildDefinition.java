package org.slinkyframework.environment.builder.definition;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AbstractBuildDefinition implements BuildDefinition, Comparable<BuildDefinition> {

    private final BuildPriority priority;
    private final String name;

    public AbstractBuildDefinition(String name) {
        this(BuildPriority.NORMAL, name);
    }

    public AbstractBuildDefinition(BuildPriority priority, String name) {
        this.priority = priority;
        this.name = name;
    }

    @Override
    public BuildPriority getPriority() {
        return priority;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @Override
    public int compareTo(BuildDefinition o) {
        return new CompareToBuilder()
                .append(priority, o.getPriority())
                .append(name, o.getName())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
