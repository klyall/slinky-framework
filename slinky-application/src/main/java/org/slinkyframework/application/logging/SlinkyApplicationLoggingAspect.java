package org.slinkyframework.application.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class SlinkyApplicationLoggingAspect extends AbstractSlinkyApplicationLoggingAspect {

    @Around("org.slinkyframework.application.SlinkyApplicationArchitecture.applicationOperations()")
    public Object loggingAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return super.loggingAdvice(proceedingJoinPoint);
    }
}
