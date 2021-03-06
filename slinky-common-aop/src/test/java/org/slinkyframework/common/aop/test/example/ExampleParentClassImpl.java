package org.slinkyframework.common.aop.test.example;

import org.slinkyframework.common.aop.domain.AnnotatedObject;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

public class ExampleParentClassImpl extends AspectObserver implements ExampleParentClass {

    private String className;
    private String methodName;
    private Optional<Annotation> returnValue;
    private List<AnnotatedObject> arguments;

    public void doParentName() {
    }

    @Override
    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String getMethodName() {
        return methodName;
    }

    @Override
    public Optional<Annotation> getReturnValue() {
        return returnValue;
    }

    @Override
    public List<AnnotatedObject> getArguments() {
        return arguments;
    }

    @Override
    public void setReturnValue(Optional<Annotation> returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public void setArguments(List<AnnotatedObject> arguments) {
        this.arguments = arguments;
    }
}
