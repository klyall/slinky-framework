package org.slinkyframework.service.mq.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slinkyframework.service.logging.AbstractServiceLoggingAspect;

@Aspect
public class MqServiceLoggingAspect extends AbstractServiceLoggingAspect {

    @Around("org.slinkyframework.service.mq.MqServiceArchitecture.serviceEndpoint()")
    public Object loggingAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return super.loggingAdvice(proceedingJoinPoint);
    }
}
