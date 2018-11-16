package org.slinkyframework.common.logging.formatters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slinkyframework.common.aop.domain.AnnotatedObject;
import org.slinkyframework.common.logging.Loggable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class LoggableTemporalFormatterTest {

    private Loggable mockLoggable;
    private Temporal testTemporal;

    public LoggableTemporalFormatterTest(String scenarion, Temporal testTemporal) {
        this.testTemporal = testTemporal;
    }

    private LoggableFormatter testee = new LoggableTemporalFormatter();

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> createScenarios() {
        List<Object[]> scenarios = new ArrayList<>();

        scenarios.add(new Object[] {"LocalDate", LocalDate.now()});
        scenarios.add(new Object[] {"LocalDateTime", LocalDateTime.now()});
        scenarios.add(new Object[] {"LocalTime", LocalTime.now()});
        scenarios.add(new Object[] {"ZonedDateTime", ZonedDateTime.now()});

        return scenarios;
    }

    @Before
    public void setUp() {
        mockLoggable = mock(Loggable.class);
        when(mockLoggable.value()).thenReturn("");
    }

    @Test
    public void shouldFormatTemporalWithoutAnnotationName() {
        AnnotatedObject annotatedObject = new AnnotatedObject(testTemporal, mockLoggable);

        assertThat("Formatted string", testee.format(annotatedObject), is(testTemporal.toString()));
    }

    @Test
    public void shouldFormatTemporalWithAnnotationName() {
        when(mockLoggable.value()).thenReturn("name");

        AnnotatedObject annotatedObject = new AnnotatedObject(testTemporal, mockLoggable);

        assertThat("Formatted string", testee.format(annotatedObject), is("name=" + testTemporal.toString()));
    }

    @Test
    public void shouldFormatNullTemporal() {
        Temporal testTemporal = null;

        AnnotatedObject annotatedObject = new AnnotatedObject(testTemporal, mockLoggable);

        assertThat("", testee.format(annotatedObject), is("null"));
    }
}
