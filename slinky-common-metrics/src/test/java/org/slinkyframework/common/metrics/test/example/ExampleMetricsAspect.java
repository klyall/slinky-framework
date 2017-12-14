package org.slinkyframework.common.metrics.test.example;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slinkyframework.common.metrics.AbstractMetricsAspect;

@Aspect
public class ExampleMetricsAspect extends AbstractMetricsAspect {

    @Pointcut("within(org.slinkyframework.common.metrics.test.example.ExampleClass)")
    public void exampleClass() {}

    @Pointcut("execution(public * *(..))")
    public void publicOperations() {}

    @Pointcut("publicOperations() && exampleClass()")
    public void exampleOperations() {
    }

    @Around("exampleOperations()")
    public Object interceptMethodName(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return super.metricsAdvice(proceedingJoinPoint);
    }

    @Override
    protected String getComponentType() {
        return "example";
    }
}
