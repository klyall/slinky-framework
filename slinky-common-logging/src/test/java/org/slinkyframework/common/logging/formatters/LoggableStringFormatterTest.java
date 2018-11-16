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
public class LoggableStringFormatterTest {

    @Mock
    private Loggable mockLoggable;

    private LoggableFormatter testee = new LoggableStringFormatter();

    @Before
    public void setUp() {
        when(mockLoggable.value()).thenReturn("");
    }

    @Test
    public void shouldFormatStringWithouAnnotationNameWithQuotes() {
        String testString = "example";

        AnnotatedObject annotatedObject = new AnnotatedObject(testString, mockLoggable);

        assertThat("Formatted string", testee.format(annotatedObject), is("'example'"));
    }

    @Test
    public void shouldFormatStringWithAnnotationNameWithQuotes() {
        String testString = "1234567890";
        when(mockLoggable.value()).thenReturn("accountId");

        AnnotatedObject annotatedObject = new AnnotatedObject(testString, mockLoggable);

        assertThat("Formatted string", testee.format(annotatedObject), is("accountId='1234567890'"));
    }

    @Test
    public void shouldFormatNullString() {
        String testString = null;

        AnnotatedObject annotatedObject = new AnnotatedObject(testString, mockLoggable);

        assertThat("Formatted string", testee.format(annotatedObject), is("null"));
    }
}
