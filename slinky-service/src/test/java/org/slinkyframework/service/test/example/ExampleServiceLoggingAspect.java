package org.slinkyframework.service.test.example;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slinkyframework.service.logging.AbstractServiceLoggingAspect;

@Aspect
public class ExampleServiceLoggingAspect extends AbstractServiceLoggingAspect {

    @Around("ExampleServiceArchitecture.serviceEndpoints()")
    public Object loggingAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return super.loggingAdvice(proceedingJoinPoint);
    }
}
