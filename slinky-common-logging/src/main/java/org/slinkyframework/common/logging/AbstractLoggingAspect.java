package org.slinkyframework.common.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slinkyframework.common.aop.MethodProceedingJoinPoint;
import org.slinkyframework.common.aop.domain.AnnotatedObject;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

import static org.slinkyframework.common.logging.ParameterExtracter.convertLoggableParametersToString;
import static org.slinkyframework.common.logging.ParameterExtracter.convertLoggableReturnToString;

public abstract class AbstractLoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractLoggingAspect.class);

    private MethodProceedingJoinPoint methodProceedingJoinPoint;
    private long duration;
    private Object returnValue;
    private Throwable exception;

    protected abstract String createLogBeforeMessage();
    protected abstract String createLogAfterMessage();
    protected abstract String createLogExceptionMessage();

    public Object loggingAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        methodProceedingJoinPoint= new MethodProceedingJoinPoint(proceedingJoinPoint);

        Object localReturnValue = null;
        long startTime = System.currentTimeMillis();

        try {
            LOGGER.info(createLogBeforeMessage());

            returnValue = methodProceedingJoinPoint.proceed();

            // Used to prevent losing the value during resetState()
            localReturnValue = returnValue;

            duration = calculateDuration(startTime);

            LOGGER.info(createLogAfterMessage());
        } catch (Throwable throwable) {
            duration = calculateDuration(startTime);
            exception = throwable;
            LOGGER.info(createLogExceptionMessage(), throwable.getMessage());
            throw throwable;
        } finally {
            resetState();
        }

        return localReturnValue;
    }

    protected long getDurationInMs() {
        return duration;
    }

    protected Throwable getException() {
        return exception;
    }

    protected String getClassName() {
        return methodProceedingJoinPoint.getClassName();
    }

    protected String getMethodName() {
        return methodProceedingJoinPoint.getMethodName();
    }

    protected String getLoggableParameters() {
        List<AnnotatedObject> args = retrieveLoggableParameters();
        return convertLoggableParametersToString(args);
    }

    protected String getLoggableReturn() {
        Optional<Annotation> annotation = methodProceedingJoinPoint.getReturnAnnotationIfType(Loggable.class);

        if (annotation.isPresent()) {
            return convertLoggableReturnToString(new AnnotatedObject(returnValue, annotation.get()));
        } else {
            return "[]";
        }
    }

    private List<AnnotatedObject> retrieveLoggableParameters() {
        return methodProceedingJoinPoint.getArgsWithAnnotation(Loggable.class);
    }

    private long calculateDuration(long startTime) {
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    private void resetState() {
        duration = 0;
        exception = null;
        methodProceedingJoinPoint = null;
        returnValue = null;
    }
}

