package org.slinkyframework.repository.metrics;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class SlinkyRepositoryMetricsAspect extends AbstractSlinkyRepositoryMetricsAspect {

    @Around("org.slinkyframework.repository.SlinkyRepositoryArchitecture.repositoryOperations()")
    public Object loggingAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return super.metricsAdvice(proceedingJoinPoint);
    }
}
