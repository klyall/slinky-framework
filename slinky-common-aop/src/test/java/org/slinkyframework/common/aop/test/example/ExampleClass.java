package org.slinkyframework.common.aop.test.example;

public interface ExampleClass extends ExampleParentClass {

    void doClassName();
    void doMethodName();
    void doMethodWithAnnotatedParameters(@ExampleAnnotation String name, String nullableName);
    @ExampleAnnotation String doMethodWithAnnotatedReturn();
}
