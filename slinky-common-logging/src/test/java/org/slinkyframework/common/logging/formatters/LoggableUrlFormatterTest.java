package org.slinkyframework.common.logging.formatters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slinkyframework.common.aop.domain.AnnotatedObject;
import org.slinkyframework.common.logging.Loggable;

import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoggableUrlFormatterTest {

    @Mock
    private Loggable mockLoggable;

    private LoggableFormatter testee = new LoggableUrlFormatter();

    @Before
    public void setUp() {
        when(mockLoggable.value()).thenReturn("");
    }

    @Test
    public void shouldFormatURLWithoutAnnotationName() throws MalformedURLException {
        String expectedUrl = "http://www.google.com";
        URL testURL = new URL(expectedUrl);

        AnnotatedObject annotatedObject = new AnnotatedObject(testURL, mockLoggable);

        assertThat("Formatted string", testee.format(annotatedObject), is(expectedUrl));
    }

    @Test
    public void shouldFormatURLWithAnnotationName() throws MalformedURLException {
        String expectedUrl = "https://www.slinkyframework.org";
        URL testURL = new URL(expectedUrl);

        when(mockLoggable.value()).thenReturn("url");

        AnnotatedObject annotatedObject = new AnnotatedObject(testURL, mockLoggable);

        assertThat("Formatted string", testee.format(annotatedObject), is("url=" + expectedUrl));
    }

    @Test
    public void shouldFormatNullURL() {
        URL testURL = null;

        AnnotatedObject annotatedObject = new AnnotatedObject(testURL, mockLoggable);

        assertThat("", testee.format(annotatedObject), is("null"));
    }
}
