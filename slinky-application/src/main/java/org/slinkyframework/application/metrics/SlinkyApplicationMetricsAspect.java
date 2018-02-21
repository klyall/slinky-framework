package org.slinkyframework.application.metrics;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slinkyframework.common.metrics.AbstractMetricsAspect;

@Aspect
public class SlinkyApplicationMetricsAspect extends AbstractMetricsAspect {

    public static final String APPLICATION = "application";

    @Override
    protected String getComponentType() {
        return APPLICATION;
    }

    @Around("org.slinkyframework.application.SlinkyApplicationArchitecture.applicationOperations()")
    public Object loggingAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return super.metricsAdvice(proceedingJoinPoint);
    }
}
