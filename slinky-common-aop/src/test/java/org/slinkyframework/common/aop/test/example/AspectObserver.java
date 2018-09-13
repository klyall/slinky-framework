package org.slinkyframework.common.aop.test.example;

import org.slinkyframework.common.aop.domain.AnnotatedObject;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

public class AspectObserver {

    private String className;
    private String methodName;
    private Optional<Annotation> returnValue;
    private List<AnnotatedObject> arguments;

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public Optional<Annotation> getReturnValue() {
        return returnValue;
    }

    public List<AnnotatedObject> getArguments() {
        return arguments;
    }

    public void setReturnValue(Optional<Annotation> returnValue) {
        this.returnValue = returnValue;
    }

    public void setArguments(List<AnnotatedObject> arguments) {
        this.arguments = arguments;
    }
}
