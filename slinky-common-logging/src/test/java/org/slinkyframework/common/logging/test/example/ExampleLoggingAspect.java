package org.slinkyframework.common.logging.test.example;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slinkyframework.common.logging.AbstractLoggingAspect;
import org.slinkyframework.common.logging.domain.LogAfterContext;
import org.slinkyframework.common.logging.domain.LogBeforeContext;
import org.slinkyframework.common.logging.domain.LogExceptionContext;

import static java.lang.String.format;

@Aspect
public class ExampleLoggingAspect extends AbstractLoggingAspect {

    @Override
    protected String createLogBeforeMessage(LogBeforeContext context) {
        return format("Before - ClassName: %s - MethodName: %s - Arguments: %s", context.getClassName(), context.getMethodName(), context.getLoggableParameters());
    }

    @Override
    protected String createLogAfterMessage(LogAfterContext context) {
        return format("After - ClassName: %s - MethodName: %s - Response time: [%d] ms %s", context.getClassName(), context.getMethodName(), context.getDurationInMs(), context.getLoggableReturn());
    }

    @Override
    protected String createLogExceptionMessage(LogExceptionContext context) {
        return format("After - ClassName: %s - MethodName: %s - Returned exception '%s' in [%d] ms", context.getClassName(), context.getMethodName(), context.getException().getMessage(), context.getDurationInMs());
    }

    @Pointcut("within(org.slinkyframework.common.logging.test.example.ExampleClass)")
    public void exampleClass() {}

    @Pointcut("execution(public * *(..))")
    public void publicOperations() {}

    @Pointcut("publicOperations() && exampleClass()")
    public void exampleOperations() {
    }

    @Around("exampleOperations()")
    public Object interceptMethodName(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return super.loggingAdvice(proceedingJoinPoint);
    }

}
