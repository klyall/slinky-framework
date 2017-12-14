package org.slinkyframework.repository.metrics;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slinkyframework.common.metrics.AbstractMetricsAspect;

@Aspect
public class SlinkyRepositoryMetricsAspect extends AbstractMetricsAspect {

    public static final String REPOSITORY = "repository";

    @Override
    protected String getComponentType() {
        return REPOSITORY;
    }

    @Around("org.slinkyframework.repository.SlinkyRepositoryArchitecture.repositoryOperations()")
    public Object loggingAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return super.metricsAdvice(proceedingJoinPoint);
    }
}
