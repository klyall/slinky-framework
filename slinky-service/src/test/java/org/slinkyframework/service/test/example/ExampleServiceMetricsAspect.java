package org.slinkyframework.service.test.example;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slinkyframework.service.metrics.AbstractServiceMetricsAspect;

@Aspect
public class ExampleServiceMetricsAspect extends AbstractServiceMetricsAspect {

    @Around("ExampleServiceArchitecture.serviceEndpoints()")

    public Object loggingAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return super.metricsAdvice(proceedingJoinPoint);
    }
}
