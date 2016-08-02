package org.slinkyframework.environment.builder;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class EnvironmentBuilderContext {

    private final String targetHost;
    private final boolean useDocker;

    public EnvironmentBuilderContext(String targetHost, boolean useDocker) {
        this.targetHost = targetHost;
        this.useDocker = useDocker;
    }

    public String getTargetHost() {
        return targetHost;
    }

    public boolean isUseDocker() {
        return useDocker;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }
}
