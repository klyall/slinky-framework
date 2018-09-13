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

    @Pointcut("execution(* org.slinkyframework.common.aop.test.example.ExampleClass+.*(..))")
    public void exampleClass() {}

    @Pointcut("execution(* org.slinkyframework.common.aop.test.example.ExampleParentClass+.*(..))")
    public void exampleParentClass() {}

    @Pointcut("execution(* org.slinkyframework.common.aop.test.example.ExampleClassWithoutInterface.*(..))")
    public void exampleClassWithoutInterface() {}

    @Pointcut("execution(public * *.do*(..))")
    public void publicOperations() {}

    @Pointcut("publicOperations() && (exampleClass() || exampleParentClass() || exampleClassWithoutInterface())")
    public void exampleOperations() {
    }

    @Around("exampleOperations()")
    public Object interceptMethodName(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodProceedingJoinPoint mpjp = new MethodProceedingJoinPoint(proceedingJoinPoint);

        String className = mpjp.getClassName();
        String methodName = mpjp.getMethodName();
        List<AnnotatedObject> arguments = mpjp.getArgsWithAnnotation(ExampleAnnotation.class);
        Optional<Annotation> returnValue = mpjp.getReturnAnnotationIfType(ExampleAnnotation.class);

        AspectObserver aspectObserver =((AspectObserver) proceedingJoinPoint.getTarget());

        aspectObserver.setClassName(className);
        aspectObserver.setMethodName(methodName);
        aspectObserver.setArguments(arguments);
        aspectObserver.setReturnValue(returnValue);

        return mpjp.proceed();
    }
}
