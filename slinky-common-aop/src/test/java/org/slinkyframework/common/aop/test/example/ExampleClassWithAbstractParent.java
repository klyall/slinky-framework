package org.slinkyframework.common.aop.test.example;

public class ExampleClassWithAbstractParent extends ExampleAbstractParentClass {

    public void doClassName() {
    }

    public void doMethodName() {
    }

    public @ExampleAnnotation String doMethodWithAnnotatedReturn() {
        return "ping";
    }
}