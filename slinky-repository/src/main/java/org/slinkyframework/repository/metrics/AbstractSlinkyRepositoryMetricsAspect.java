package org.slinkyframework.repository.metrics;

import org.slinkyframework.common.metrics.AbstractMetricsAspect;

public class AbstractSlinkyRepositoryMetricsAspect extends AbstractMetricsAspect {

    public static final String REPOSITORY = "repository";

    @Override
    protected String getComponentType() {
        return REPOSITORY;
    }
}
