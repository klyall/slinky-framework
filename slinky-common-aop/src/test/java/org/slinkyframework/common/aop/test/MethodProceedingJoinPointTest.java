package org.slinkyframework.common.aop.test;

import org.junit.Test;
import org.slinkyframework.common.aop.test.example.ExampleClassImpl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MethodProceedingJoinPointTest {

    @Test
    public void testGetClassName() throws Throwable {
        ExampleClassImpl exampleClass = new ExampleClassImpl();
        exampleClass.doClassName();

        assertThat("ClassName", exampleClass.getClassName(), is("ExampleClass"));
    }

    @Test
    public void testGetMethodName() throws Throwable {
        ExampleClassImpl exampleClass = new ExampleClassImpl();
        exampleClass.doMethodName();

        assertThat("MethodName", exampleClass.getMethodName(), is("doMethodName"));
    }

    @Test
    public void testGetParentMethodName() throws Throwable {
        ExampleClassImpl exampleClass = new ExampleClassImpl();
        exampleClass.doParentName();

        assertThat("ClassName", exampleClass.getClassName(), is("ExampleClass"));
        assertThat("MethodName", exampleClass.getMethodName(), is("doParentName"));
    }

    @Test
    public void testGetArgsWithAnnotation() throws Throwable {
        ExampleClassImpl exampleClass = new ExampleClassImpl();
        exampleClass.doMethodWithAnnotatedParameters("Bob", "Smith");

        assertThat("Number of arguments", exampleClass.getArguments().size(), is(1));
    }

    @Test
    public void testGetReturnWithAnnotation() throws Throwable {
        ExampleClassImpl exampleClass = new ExampleClassImpl();
        exampleClass.doMethodWithAnnotatedReturn();

        assertThat("Number of arguments", exampleClass.getReturnValue().isPresent(), is(true));
    }
}
