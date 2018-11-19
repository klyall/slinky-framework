package org.slinkyframework.common.aop.test.example;

public abstract class ExampleAbstractParentClass extends AspectObserver {

    public void doClassName() {
    }

    public void doMethodName() {
    }

    public void doMethodWithAnnotatedParameters(@ExampleAnnotation String name, String nullableName) {
    }
}
