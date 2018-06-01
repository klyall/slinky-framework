package org.slinkyframework.client.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class SlinkyClientLoggingAspect extends AbstractSlinkyClientLoggingAspect {

    @Around("org.slinkyframework.client.SlinkyClientArchitecture.clientOperations()")
    public Object loggingAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return super.loggingAdvice(proceedingJoinPoint);
    }
}
