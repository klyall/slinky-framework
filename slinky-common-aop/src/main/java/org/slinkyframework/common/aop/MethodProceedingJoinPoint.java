package org.slinkyframework.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.SourceLocation;
import org.aspectj.runtime.internal.AroundClosure;

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
        String className = proceedingJoinPoint.getSourceLocation().getWithinType().getSimpleName();

        if (className.endsWith("Impl")) {
            return className.substring(0, className.indexOf("Impl"));
        } else {
            return className;
        }
    }

    public String getMethodName() {
        return proceedingJoinPoint.getSignature().getName();
    }
}
