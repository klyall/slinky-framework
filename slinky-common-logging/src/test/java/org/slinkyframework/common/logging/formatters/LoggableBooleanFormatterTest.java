package org.slinkyframework.common.logging.formatters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slinkyframework.common.aop.domain.AnnotatedObject;
import org.slinkyframework.common.logging.Loggable;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoggableBooleanFormatterTest {

    @Mock
    private Loggable mockLoggable;

    private LoggableFormatter testee = new LoggableBooleanFormatter();

    @Before
    public void setUp() {
        when(mockLoggable.value()).thenReturn("");
    }

    @Test
    public void shouldFormatBooleanWithoutAnnotationName() {
        boolean testBoolean = true;

        AnnotatedObject annotatedObject = new AnnotatedObject(testBoolean, mockLoggable);

        assertThat("Formatted string", testee.format(annotatedObject), is("true"));
    }

    @Test
    public void shouldFormatBooleanWithAnnotationName() {
        boolean testBoolean = true;

        when(mockLoggable.value()).thenReturn("success");

        AnnotatedObject annotatedObject = new AnnotatedObject(testBoolean, mockLoggable);

        assertThat("Formatted string", testee.format(annotatedObject), is("success=true"));
    }

    @Test
    public void shouldFormatNullBoolean() {
        Boolean testBoolean = null;

        AnnotatedObject annotatedObject = new AnnotatedObject(testBoolean, mockLoggable);

        assertThat("", testee.format(annotatedObject), is("null"));
    }
}
