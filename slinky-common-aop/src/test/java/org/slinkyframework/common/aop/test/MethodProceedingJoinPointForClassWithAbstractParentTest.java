package org.slinkyframework.common.aop.test;

import org.junit.Test;
import org.slinkyframework.common.aop.test.example.ExampleClassWithAbstractParent;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MethodProceedingJoinPointForClassWithAbstractParentTest {

    @Test
    public void testGetClassName() throws Throwable {
        ExampleClassWithAbstractParent exampleClass = new ExampleClassWithAbstractParent();
        exampleClass.doClassName();

        assertThat("ClassName", exampleClass.getClassName(), is("ExampleClassWithAbstractParent"));
    }

    @Test
    public void testGetMethodName() throws Throwable {
        ExampleClassWithAbstractParent exampleClass = new ExampleClassWithAbstractParent();
        exampleClass.doMethodName();

        assertThat("MethodName", exampleClass.getMethodName(), is("doMethodName"));
    }

    @Test
    public void testGetArgsWithAnnotation() throws Throwable {
        ExampleClassWithAbstractParent exampleClass = new ExampleClassWithAbstractParent();
        exampleClass.doMethodWithAnnotatedParameters("Bob", "Smith");

        assertThat("Number of arguments", exampleClass.getArguments().size(), is(1));
    }

    @Test
    public void testGetReturnWithAnnotation() throws Throwable {
        ExampleClassWithAbstractParent exampleClass = new ExampleClassWithAbstractParent();
        exampleClass.doMethodWithAnnotatedReturn();

        assertThat("Number of arguments", exampleClass.getReturnValue().isPresent(), is(true));
    }
}
