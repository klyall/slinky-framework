package org.slinkyframework.application.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slinkyframework.common.logging.AbstractLoggingAspect;
import org.slinkyframework.common.logging.domain.LogAfterContext;
import org.slinkyframework.common.logging.domain.LogBeforeContext;
import org.slinkyframework.common.logging.domain.LogExceptionContext;

import static java.lang.String.format;

@Aspect
public class SlinkyApplicationLoggingAspect extends AbstractLoggingAspect {

    public static final String LOG_BEFORE       = "----> %s %s request received %s";
    public static final String LOG_AFTER        = "<---- %s %s response returned in [%d] ms. %s";
    public static final String LOG_EXCEPTION    = "<---- %s %s exception returned in [%d] ms., exception message [%s]";

    private static final Logger LOGGER = LoggerFactory.getLogger(SlinkyApplicationLoggingAspect.class);

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

    @Around("org.slinkyframework.application.SlinkyApplicationArchitecture.applicationOperations()")
    public Object loggingAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return super.loggingAdvice(proceedingJoinPoint);
    }
}
