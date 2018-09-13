package org.slinkyframework.service.metrics;

import org.aspectj.lang.annotation.Aspect;
import org.slinkyframework.common.metrics.AbstractMetricsAspect;

@Aspect
public abstract class AbstractServiceMetricsAspect extends AbstractMetricsAspect {

    public static final String SERVICE = "service";

    @Override
    protected String getComponentType() {
        return SERVICE;
    }
}
