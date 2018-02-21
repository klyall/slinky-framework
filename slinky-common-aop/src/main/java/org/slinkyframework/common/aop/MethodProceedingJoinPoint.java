package org.slinkyframework.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;
import org.aspectj.runtime.internal.AroundClosure;
import org.slinkyframework.common.aop.domain.AnnotatedObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class MethodProceedingJoinPoint implements ProceedingJoinPoint {

    private ProceedingJoinPoint proceedingJoinPoint;

    public MethodProceedingJoinPoint(ProceedingJoinPoint proceedingJoinPoint) {
        this.proceedingJoinPoint = proceedingJoinPoint;
    }

    @Override
    public void set$AroundClosure(AroundClosure aroundClosure) {
        proceedingJoinPoint.set$AroundClosure(aroundClosure);
    }

    @Override
    public Object proceed() throws Throwable {
        return proceedingJoinPoint.proceed();
    }

    @Override
    public Object proceed(Object[] objects) throws Throwable {
        return proceedingJoinPoint.proceed(objects);
    }

    @Override
    public String toShortString() {
        return proceedingJoinPoint.toShortString();
    }

    @Override
    public String toLongString() {
        return proceedingJoinPoint.toLongString();
    }

    @Override
    public Object getThis() {
        return proceedingJoinPoint.getThis();
    }

    @Override
    public Object getTarget() {
        return proceedingJoinPoint.getTarget();
    }

    @Override
    public Object[] getArgs() {
        return proceedingJoinPoint.getArgs();
    }

    @Override
    public Signature getSignature() {
        return proceedingJoinPoint.getSignature();
    }

    @Override
    public SourceLocation getSourceLocation() {
        return proceedingJoinPoint.getSourceLocation();
    }

    @Override
    public String getKind() {
        return proceedingJoinPoint.getKind();
    }

    @Override
    public StaticPart getStaticPart() {
        return proceedingJoinPoint.getStaticPart();
    }

    public String getClassName() {
        String className = proceedingJoinPoint.getTarget().getClass().getSimpleName();

        if (className.endsWith("Impl")) {
            return className.substring(0, className.indexOf("Impl"));
        } else {
            return className;
        }
    }

    public List<AnnotatedObject> getArgsWithAnnotation(Class annotationClass) {
        List<AnnotatedObject> annotatedObjects = new LinkedList<>();

        Object[] args = proceedingJoinPoint.getArgs();

        MethodSignature signature = getMethodSignature();
        Method m = signature.getMethod();
        Annotation[][] pa = m.getParameterAnnotations();

        for (int i = 0; i < pa.length; i++) {
            for (Annotation annotation: pa[i]) {
                if (annotation.annotationType().equals(annotationClass)) {
                    annotatedObjects.add(new AnnotatedObject(args[i], annotation));
                }
            }
        }

        return annotatedObjects;
    }

    public String getMethodName() {
        return proceedingJoinPoint.getSignature().getName();
    }

    protected MethodSignature getMethodSignature() {
        return (MethodSignature) proceedingJoinPoint.getSignature();
    }

    public Optional<Annotation> getReturnAnnotationIfType(Class annotationClass) {
        MethodSignature signature = getMethodSignature();
        Method method = signature.getMethod();
        Annotation[] annoations = method.getAnnotations();

        for (Annotation annotation: annoations) {
            if (annotation.annotationType().equals(annotationClass)) {
                return Optional.of(annotation);
            }
        }

        return Optional.empty();
    }
}
