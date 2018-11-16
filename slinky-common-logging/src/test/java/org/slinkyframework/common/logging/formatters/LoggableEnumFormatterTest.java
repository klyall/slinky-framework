package org.slinkyframework.common.logging.formatters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slinkyframework.common.aop.domain.AnnotatedObject;
import org.slinkyframework.common.logging.Loggable;
import org.slinkyframework.common.logging.test.example.ExampleEnum;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoggableEnumFormatterTest {

    @Mock
    private Loggable mockLoggable;

    private LoggableFormatter testee = new LoggableEnumFormatter();

    @Before
    public void setUp() {
        when(mockLoggable.value()).thenReturn("");
    }

    @Test
    public void shouldFormatEnumWithoutAnnotationName() {
        ExampleEnum testEnum = ExampleEnum.ONE;

        AnnotatedObject annotatedObject = new AnnotatedObject(testEnum, mockLoggable);

        assertThat("Formatted string", testee.format(annotatedObject), is("ONE"));
    }

    @Test
    public void shouldFormatEnumWithAnnotationName() {
        ExampleEnum testEnum = ExampleEnum.ONE;

        when(mockLoggable.value()).thenReturn("number");

        AnnotatedObject annotatedObject = new AnnotatedObject(testEnum, mockLoggable);

        assertThat("Formatted string", testee.format(annotatedObject), is("number=ONE"));
    }

    @Test
    public void shouldFormatNullEnum() {
        Enum testEnum = null;

        AnnotatedObject annotatedObject = new AnnotatedObject(testEnum, mockLoggable);

        assertThat("", testee.format(annotatedObject), is("null"));
    }
}
