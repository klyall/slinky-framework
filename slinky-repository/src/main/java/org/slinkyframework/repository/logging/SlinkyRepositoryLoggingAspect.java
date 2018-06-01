package org.slinkyframework.repository.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class SlinkyRepositoryLoggingAspect extends AbstractSlinkyRepositoryLoggingAspect {

    @Around("org.slinkyframework.repository.SlinkyRepositoryArchitecture.repositoryOperations()")
    public Object loggingAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return super.loggingAdvice(proceedingJoinPoint);
    }
}
