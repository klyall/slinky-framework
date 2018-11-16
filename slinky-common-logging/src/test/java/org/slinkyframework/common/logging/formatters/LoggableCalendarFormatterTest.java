package org.slinkyframework.common.logging.formatters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slinkyframework.common.aop.domain.AnnotatedObject;
import org.slinkyframework.common.logging.Loggable;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoggableCalendarFormatterTest {

    @Mock
    private Loggable mockLoggable;

    private LoggableFormatter testee = new LoggableCalendarFormatter();

    @Before
    public void setUp() {
        when(mockLoggable.value()).thenReturn("");
    }

    @Test
    public void shouldFormatCalendarWithoutAnnotationName() {
        ZonedDateTime now = ZonedDateTime.now();
        Calendar testCalendar = GregorianCalendar.from(now);

        AnnotatedObject annotatedObject = new AnnotatedObject(testCalendar, mockLoggable);

        assertThat("Formatted string", testee.format(annotatedObject), is(now.toString()));
    }

    @Test
    public void shouldFormatCalendarWithAnnotationName() {
        ZonedDateTime now = ZonedDateTime.now();
        Calendar testCalendar = GregorianCalendar.from(now);

        when(mockLoggable.value()).thenReturn("expires");

        AnnotatedObject annotatedObject = new AnnotatedObject(testCalendar, mockLoggable);

        assertThat("Formatted string", testee.format(annotatedObject), is("expires=" + now.toString()));
    }

    @Test
    public void shouldFormatNullCalendar() {
        Calendar testCalendar = null;

        AnnotatedObject annotatedObject = new AnnotatedObject(testCalendar, mockLoggable);

        assertThat("", testee.format(annotatedObject), is("null"));
    }
}
