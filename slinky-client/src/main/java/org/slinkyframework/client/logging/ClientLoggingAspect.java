package org.slinkyframework.client.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slinkyframework.common.logging.AbstractLoggingAspect;

@Aspect
public class ClientLoggingAspect extends AbstractLoggingAspect {

    public static final String LOG_BEFORE       = "----> {} {} request sent";
    public static final String LOG_AFTER        = "<---- {} {} response received in [{}] ms.";
    public static final String LOG_EXCEPTION    = "<---- {} {} exception received in [{}] ms., exception message [{}]";

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientLoggingAspect.class);

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

    @Around("org.slinkyframework.client.ClientArchitecture.clientOperations()")
    public Object loggingAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return super.loggingAdvice(proceedingJoinPoint);
    }
}
