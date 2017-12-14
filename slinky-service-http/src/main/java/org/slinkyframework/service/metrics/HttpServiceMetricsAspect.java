package org.slinkyframework.service.metrics;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slinkyframework.common.metrics.AbstractMetricsAspect;

@Aspect
public class HttpServiceMetricsAspect extends AbstractMetricsAspect {

    public static final String SERVICE = "service";

    @Override
    protected String getComponentType() {
        return SERVICE;
    }

    @Around("org.slinkyframework.service.HttpServiceArchitecture.serviceEndpoint()")
    public Object metricsAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return super.metricsAdvice(proceedingJoinPoint);
    }
}
