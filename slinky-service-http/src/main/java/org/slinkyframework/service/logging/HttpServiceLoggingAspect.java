package org.slinkyframework.service.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class HttpServiceLoggingAspect extends AbstractHttpServiceLoggingAspect {

    @Around("org.slinkyframework.service.HttpServiceArchitecture.serviceEndpoint()")
    public Object loggingAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return super.loggingAdvice(proceedingJoinPoint);
    }
}
