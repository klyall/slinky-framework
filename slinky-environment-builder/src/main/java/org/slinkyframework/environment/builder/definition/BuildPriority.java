package org.slinkyframework.environment.builder.definition;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BuildPriority implements Comparable<BuildPriority> {

    public static final BuildPriority HIGH = new BuildPriority(1);
    public static final BuildPriority NORMAL = new BuildPriority(5);
    public static final BuildPriority LOW = new BuildPriority(9);


    private int priority;

    public BuildPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }

    @Override
    public int compareTo(BuildPriority o) {
        return new CompareToBuilder()
                .append(priority, o.getPriority())
                .build();
    }
}
