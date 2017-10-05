package org.slinkyframework.service.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slinkyframework.common.logging.AbstractLoggingAspect;
import org.slinkyframework.common.logging.domain.LogAfterContext;
import org.slinkyframework.common.logging.domain.LogBeforeContext;
import org.slinkyframework.common.logging.domain.LogExceptionContext;

import static java.lang.String.format;

@Aspect
public class HttpServiceLoggingAspect extends AbstractLoggingAspect {

    public static final String LOG_BEFORE       = "--> %s %s request received %s";
    public static final String LOG_AFTER        = "<-- %s %s response returned in [%d] ms. %s";
    public static final String LOG_EXCEPTION    = "<-- %s %s exception returned in [%d] ms., exception message [%s]";

    @Override
    protected String createLogBeforeMessage(LogBeforeContext context) {
        return format(LOG_BEFORE, context.getClassName(), context.getMethodName(), context.getLoggableParameters());
    }

    @Override
    protected String createLogAfterMessage(LogAfterContext context) {
        return format(LOG_AFTER, context.getClassName(), context.getMethodName(), context.getDurationInMs(), context.getLoggableReturn());
    }

    @Override
    protected String createLogExceptionMessage(LogExceptionContext context) {
        return format(LOG_EXCEPTION, context.getClassName(), context.getMethodName(), context.getDurationInMs(), context.getException().getMessage());
    }

    @Around("org.slinkyframework.service.HttpServiceArchitecture.serviceEndpoint()")
    public Object loggingAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return super.loggingAdvice(proceedingJoinPoint);
    }
}
