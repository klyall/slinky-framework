package org.slinkyframework.common.aop.test.example;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slinkyframework.common.aop.MethodProceedingJoinPoint;
import org.slinkyframework.common.aop.domain.AnnotatedObject;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

@Aspect
public class ExampleAspect {

    private static String className;
    private static String methodName;
    private static List<AnnotatedObject> arguments;
    private static Optional<Annotation> returnValue;

    public static void cleanState() {
        className = null;
        methodName = null;
        arguments = null;
    }

    public static String getClassName() {
        return className;
    }

    public static String getMethodName() {
        return methodName;
    }

    public static Optional<Annotation> getReturnValue() {
        return returnValue;
    }

    public static List<AnnotatedObject> getArguments() {
        return arguments;
    }

    @Pointcut("within(org.slinkyframework.common.aop.test.example.ExampleClass)")
    public void exampleClass() {}

    @Pointcut("execution(public * *(..))")
    public void publicOperations() {}

    @Pointcut("publicOperations() && exampleClass()")
    public void exampleOperations() {
    }

    @Around("exampleOperations()")
    public Object interceptMethodName(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodProceedingJoinPoint mpjp = new MethodProceedingJoinPoint(proceedingJoinPoint);

        className = mpjp.getClassName();
        methodName = mpjp.getMethodName();
        arguments = mpjp.getArgsWithAnnotation(ExampleAnnotation.class);
        returnValue = mpjp.getReturnAnnotationIfType(ExampleAnnotation.class);

        return mpjp.proceed();
    }
}
