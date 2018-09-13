package org.slinkyframework.common.aop.test;

import org.junit.Test;
import org.slinkyframework.common.aop.test.example.ExampleClassWithoutInterface;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MethodProceedingJoinPointForClassWithoutInterfaceTest {

    @Test
    public void testGetClassName() throws Throwable {
        ExampleClassWithoutInterface exampleClass = new ExampleClassWithoutInterface();
        exampleClass.doClassName();

        assertThat("ClassName", exampleClass.getClassName(), is("ExampleClassWithoutInterface"));
    }

    @Test
    public void testGetMethodName() throws Throwable {
        ExampleClassWithoutInterface exampleClass = new ExampleClassWithoutInterface();
        exampleClass.doMethodName();

        assertThat("MethodName", exampleClass.getMethodName(), is("doMethodName"));
    }

    @Test
    public void testGetArgsWithAnnotation() throws Throwable {
        ExampleClassWithoutInterface exampleClass = new ExampleClassWithoutInterface();
        exampleClass.doMethodWithAnnotatedParameters("Bob", "Smith");

        assertThat("Number of arguments", exampleClass.getArguments().size(), is(1));
    }

    @Test
    public void testGetReturnWithAnnotation() throws Throwable {
        ExampleClassWithoutInterface exampleClass = new ExampleClassWithoutInterface();
        exampleClass.doMethodWithAnnotatedReturn();

        assertThat("Number of arguments", exampleClass.getReturnValue().isPresent(), is(true));
    }
}
