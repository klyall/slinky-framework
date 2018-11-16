package org.slinkyframework.common.logging.formatters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slinkyframework.common.aop.domain.AnnotatedObject;
import org.slinkyframework.common.logging.Loggable;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoggableDateFormatterTest {

    @Mock
    private Loggable mockLoggable;

    private LoggableFormatter testee = new LoggableDateFormatter();

    @Before
    public void setUp() {
        when(mockLoggable.value()).thenReturn("");
    }

    @Test
    public void shouldFormatDateWithoutAnnotationName() {
        LocalDateTime now = LocalDateTime.now();
        Date testDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());

        AnnotatedObject annotatedObject = new AnnotatedObject(testDate, mockLoggable);

        assertThat("Formatted string", testee.format(annotatedObject), is(now.toString()));
    }

    @Test
    public void shouldFormatDateWithAnnotationName() {
        LocalDateTime now = LocalDateTime.now();
        Date testDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());

        when(mockLoggable.value()).thenReturn("expiryDate");

        AnnotatedObject annotatedObject = new AnnotatedObject(testDate, mockLoggable);

        assertThat("Formatted string", testee.format(annotatedObject), is("expiryDate=" + now.toString()));
    }

    @Test
    public void shouldFormatNullDate() {
        Date testDate = null;

        AnnotatedObject annotatedObject = new AnnotatedObject(testDate, mockLoggable);

        assertThat("", testee.format(annotatedObject), is("null"));
    }
}
