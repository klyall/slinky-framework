package org.slinkyframework.repository.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slinkyframework.common.logging.AbstractLoggingAspect;
import org.slinkyframework.common.logging.domain.LogAfterContext;
import org.slinkyframework.common.logging.domain.LogBeforeContext;
import org.slinkyframework.common.logging.domain.LogExceptionContext;

import static java.lang.String.format;

public abstract class AbstractSlinkyRepositoryLoggingAspect extends AbstractLoggingAspect {

    private static final String LOG_BEFORE       = "------> %s %s query sent %s";
    private static final String LOG_AFTER        = "<------ %s %s result received in [%d] ms. %s";
    private static final String LOG_EXCEPTION    = "<------ %s %s exception received in [%d] ms., exception message [%s]";

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

    public Object loggingAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return super.loggingAdvice(proceedingJoinPoint);
    }
}
