package org.slinkyframework.client.metrics;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slinkyframework.common.metrics.AbstractMetricsAspect;

@Aspect
public class SlinkyClientMetricsAspect extends AbstractMetricsAspect {

    public static final String CLIENT = "client";

    @Override
    protected String getComponentType() {
        return CLIENT;
    }

    @Around("org.slinkyframework.client.SlinkyClientArchitecture.clientOperations()")
    public Object loggingAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return super.metricsAdvice(proceedingJoinPoint);
    }
}
