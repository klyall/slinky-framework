package org.slinkyframework.common.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slinkyframework.common.aop.MethodProceedingJoinPoint;

public abstract class AbstractLoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractLoggingAspect.class);

    protected abstract String getLogBefore();
    protected abstract String getLogAfter();
    protected abstract String getLogException();

    public Object loggingAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object returnObject = null;
        MethodProceedingJoinPoint joinPoint = new MethodProceedingJoinPoint(proceedingJoinPoint);
        long startTime = System.currentTimeMillis();

        try {
            LOGGER.info(getLogBefore(), joinPoint.getClassName(), joinPoint.getMethodName());

            returnObject = joinPoint.proceed();

            LOGGER.info(getLogAfter(), joinPoint.getClassName(), joinPoint.getMethodName(), calculateDuration(startTime));
        } catch (Throwable throwable) {
            LOGGER.info(getLogException(), joinPoint.getClassName(), joinPoint.getMethodName(), calculateDuration(startTime), throwable.getMessage());
            throw throwable;
        } finally {
        }

        return returnObject;
    }

    private long calculateDuration(long startTime) {
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
