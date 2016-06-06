package org.slinkyframework.service.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slinkyframework.common.logging.AbstractLoggingAspect;

@Aspect
public class HttpServiceLoggingAspect extends AbstractLoggingAspect {

    public static final String LOG_BEFORE       = "--> {} {} request received";
    public static final String LOG_AFTER        = "<-- {} {} response returned in [{}] ms.";
    public static final String LOG_EXCEPTION    = "<-- {} {} exception returned in [{}] ms., exception message [{}]";

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServiceLoggingAspect.class);

    @Override
    protected String getLogBefore() {
        return LOG_BEFORE;
    }

    @Override
    protected String getLogAfter() {
        return LOG_AFTER;
    }

    @Override
    protected String getLogException() {
        return LOG_EXCEPTION;
    }

    @Around("org.slinkyframework.service.HttpServiceArchitecture.serviceEndpoint()")
    public Object loggingAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return super.loggingAdvice(proceedingJoinPoint);
    }
}
