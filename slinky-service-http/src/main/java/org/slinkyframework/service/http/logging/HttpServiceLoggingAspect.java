package org.slinkyframework.service.http.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slinkyframework.service.logging.AbstractServiceLoggingAspect;

@Aspect
public class HttpServiceLoggingAspect extends AbstractServiceLoggingAspect {

    @Around("org.slinkyframework.service.http.HttpServiceArchitecture.serviceEndpoint()")
    public Object loggingAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return super.loggingAdvice(proceedingJoinPoint);
    }
}
