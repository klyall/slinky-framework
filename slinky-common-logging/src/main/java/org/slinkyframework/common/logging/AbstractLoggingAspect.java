package org.slinkyframework.common.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slinkyframework.common.aop.MethodProceedingJoinPoint;
import org.slinkyframework.common.aop.domain.AnnotatedObject;
import org.slinkyframework.common.logging.domain.LogAfterContext;
import org.slinkyframework.common.logging.domain.LogBeforeContext;
import org.slinkyframework.common.logging.domain.LogExceptionContext;
import org.slinkyframework.common.logging.formatters.ParameterFormatter;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

public abstract class AbstractLoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractLoggingAspect.class);

    protected abstract String createLogBeforeMessage(LogBeforeContext context);
    protected abstract String createLogAfterMessage(LogAfterContext context);
    protected abstract String createLogExceptionMessage(LogExceptionContext context);

    private ParameterFormatter parameterFormatter = new ParameterFormatter();

    public Object loggingAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodProceedingJoinPoint methodProceedingJoinPoint= new MethodProceedingJoinPoint(proceedingJoinPoint);

        Object returnValue = null;
        long startTime = System.currentTimeMillis();

        try {
            LOGGER.info(createLogBeforeMessage(
                    createLogBeforeContext(methodProceedingJoinPoint)));

            returnValue = methodProceedingJoinPoint.proceed();

            LOGGER.info(createLogAfterMessage(
                    createLogAfterContext(
                            methodProceedingJoinPoint,
                            calculateDuration(startTime),
                            returnValue)));

        } catch (Throwable throwable) {
            LOGGER.info(createLogExceptionMessage(
                    createLogExceptionContext(
                            methodProceedingJoinPoint,
                            calculateDuration(startTime),
                            throwable)));

            throw throwable;
        }

        return returnValue;
    }


    private LogBeforeContext createLogBeforeContext(MethodProceedingJoinPoint methodProceedingJoinPoint) {
        return new LogBeforeContext(
                methodProceedingJoinPoint.getClassName(),
                methodProceedingJoinPoint.getMethodName(),
                formatLoggableParameters(methodProceedingJoinPoint));
    }

    private String formatLoggableParameters(MethodProceedingJoinPoint methodProceedingJoinPoint) {
        List<AnnotatedObject> args = methodProceedingJoinPoint.getArgsWithAnnotation(Loggable.class);

        return parameterFormatter.format(args);
    }

    private LogAfterContext createLogAfterContext(MethodProceedingJoinPoint methodProceedingJoinPoint, long duration, Object returnValue) {
        return new LogAfterContext(
                methodProceedingJoinPoint.getClassName(),
                methodProceedingJoinPoint.getMethodName(),
                duration,
                formatLoggableReturn(methodProceedingJoinPoint, returnValue));
    }

    private  String formatLoggableReturn(MethodProceedingJoinPoint methodProceedingJoinPoint, Object returnValue) {
        Optional<Annotation> annotation = methodProceedingJoinPoint.getReturnAnnotationIfType(Loggable.class);

        if (annotation.isPresent()) {
            return parameterFormatter.format(new AnnotatedObject(returnValue, annotation.get()));
        } else {
            return "[]";
        }
    }

    private LogExceptionContext createLogExceptionContext(MethodProceedingJoinPoint methodProceedingJoinPoint, long duration, Throwable throwable) {
        return new LogExceptionContext(
            methodProceedingJoinPoint.getClassName(),
                methodProceedingJoinPoint.getMethodName(),
                duration,
                throwable
        );
    }

    private long calculateDuration(long startTime) {
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}

