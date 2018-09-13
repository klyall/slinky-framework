package org.slinkyframework.service.mq.metrics;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slinkyframework.service.metrics.AbstractServiceMetricsAspect;

@Aspect
public class MqServiceMetricsAspect extends AbstractServiceMetricsAspect {

    @Around("org.slinkyframework.service.mq.MqServiceArchitecture.serviceEndpoint()")
    public Object metricsAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return super.metricsAdvice(proceedingJoinPoint);
    }
}
