package org.slinkyframework.common.logging.formatters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slinkyframework.common.aop.domain.AnnotatedObject;
import org.slinkyframework.common.logging.Loggable;
import org.slinkyframework.common.logging.test.example.ClassWithoutAccessor;
import org.slinkyframework.common.logging.test.example.ExampleName;
import org.slinkyframework.common.logging.test.example.ExamplePerson;
import org.slinkyframework.common.logging.test.example.ExampleSavingsAccount;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class LoggableObjectFormatterTest {

    private Loggable mockLoggable;
    private final Object testObject;
    private final String expectedOutput;

    public LoggableObjectFormatterTest(String scenario, Object testObject, String expectedOutput) {
        this.testObject = testObject;
        this.expectedOutput = expectedOutput;
    }

    private LoggableFormatter testee = new LoggableObjectFormatter(new LoggableFormatterFactory());

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> createScenarios() {
        List<Object[]> scenarios = new ArrayList<>();

        ExampleName exampleName = new ExampleName("Joe", "Bloggs");
        scenarios.add(new Object[] {"ExampleName", exampleName, "(firstName='Joe')"});
        scenarios.add(new Object[] {"ExamplePerson", new ExamplePerson(1, exampleName), "(id=1, name=(firstName='Joe'))"});
        scenarios.add(new Object[] {"ClassWithoutAccessor", new ClassWithoutAccessor("string1", "string2", "string3"), "(string1='string1', string2='NOT_VISIBLE', string3='NOT_VISIBLE')"});
        scenarios.add(new Object[] {"AbstractClass", new ExampleSavingsAccount("1234567890", 100.00), "(accountNumber='1234567890', balance=100.0)"});

        return scenarios;
    }

    @Before
    public void setUp() {
        mockLoggable = mock(Loggable.class);
        when(mockLoggable.value()).thenReturn("");
    }

    @Test
    public void shouldFormatObjectWithoutAnnotationName() {
        AnnotatedObject annotatedObject = new AnnotatedObject(testObject, mockLoggable);

        assertThat("Formatted string", testee.format(annotatedObject), is(expectedOutput));
    }

    @Test
    public void shouldFormatObjectWithAnnotationName() {
        when(mockLoggable.value()).thenReturn("name");

        AnnotatedObject annotatedObject = new AnnotatedObject(testObject, mockLoggable);

        assertThat("Formatted string", testee.format(annotatedObject), is("name=" + expectedOutput));
    }

    @Test
    public void shouldFormatNullObject() {
        Object testObject = null;

        AnnotatedObject annotatedObject = new AnnotatedObject(testObject, mockLoggable);

        assertThat("", testee.format(annotatedObject), is("null"));
    }
}
