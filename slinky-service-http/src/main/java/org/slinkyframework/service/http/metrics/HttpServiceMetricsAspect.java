package org.slinkyframework.service.http.metrics;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slinkyframework.service.metrics.AbstractServiceMetricsAspect;

@Aspect
public class HttpServiceMetricsAspect extends AbstractServiceMetricsAspect {

    @Around("org.slinkyframework.service.http.HttpServiceArchitecture.serviceEndpoint()")
    public Object metricsAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return super.metricsAdvice(proceedingJoinPoint);
    }
}
