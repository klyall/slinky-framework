package org.slinkyframework.common.logging.test.example;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slinkyframework.common.logging.AbstractLoggingAspect;

import static java.lang.String.format;

@Aspect
public class ExampleLoggingAspect extends AbstractLoggingAspect {

    @Override
    protected String createLogBeforeMessage() {
        return format("Before - ClassName: %s - MethodName: %s - Arguments: %s", getClassName(), getMethodName(), getLoggableParameters());
    }

    @Override
    protected String createLogAfterMessage() {
        return format("After - ClassName: %s - MethodName: %s - Response time: [%d] ms %s", getClassName(), getMethodName(), getDurationInMs(), getLoggableReturn());
    }

    @Override
    protected String createLogExceptionMessage() {
        return "Exception";
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
