package org.slinkyframework.common.aop.test;

import org.junit.Before;
import org.junit.Test;
import org.slinkyframework.common.aop.test.example.ExampleAspect;
import org.slinkyframework.common.aop.test.example.ExampleClass;
import org.slinkyframework.common.aop.test.example.ExampleClassImpl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MethodProceedingJoinPointTest {

    private ExampleClass exampleClass = new ExampleClassImpl();

    @Before
    public void setUp() {
        ExampleAspect.cleanState();
    }

    @Test
    public void testGetClassName() throws Throwable {
        exampleClass.doClassName();

        assertThat("ClassName", ExampleAspect.getClassName(), is("ExampleClass"));
    }

    @Test
    public void testGetMethodName() throws Throwable {
        exampleClass.doMethodName();

        assertThat("MethodName", ExampleAspect.getMethodName(), is("doMethodName"));
    }

    @Test
    public void testGetParentMethodName() throws Throwable {
        exampleClass.doParentName();

        assertThat("ClassName", ExampleAspect.getClassName(), is("ExampleClass"));
        assertThat("MethodName", ExampleAspect.getMethodName(), is("doParentName"));
    }

    @Test
    public void testGetArgsWithAnnotation() throws Throwable {
        exampleClass.doMethodWithAnnotatedParameters("Bob", "Smith");

        assertThat("Number of arguments", ExampleAspect.getArguments().size(), is(1));
    }

    @Test
    public void testGetReturnWithAnnotation() throws Throwable {
        exampleClass.doMethodWithAnnotatedReturn();

        assertThat("Number of arguments", ExampleAspect.getReturnValue().isPresent(), is(true));
    }
}
