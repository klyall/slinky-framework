package org.slinkyframework.common.logging.formatters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slinkyframework.common.aop.domain.AnnotatedObject;
import org.slinkyframework.common.logging.Loggable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class LoggableNumberFormatterTest {

    private Loggable mockLoggable;
    private final Number testNumber;
    private final String expectedOutput;

    public LoggableNumberFormatterTest(String scenario, Number testNumber, String expectedOutput) {
        this.testNumber = testNumber;
        this.expectedOutput = expectedOutput;
    }

    private LoggableFormatter testee = new LoggableNumberFormatter();

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> createScenarios() {
        List<Object[]> scenarios = new ArrayList<>();

        scenarios.add(new Object[] {"Integer", 1, "1"});
        scenarios.add(new Object[] {"Long", 100L, "100"});
        scenarios.add(new Object[] {"Double", 1000.00, "1000.0"});
        scenarios.add(new Object[] {"Float", 2000.00f, "2000.0"});
        scenarios.add(new Object[] {"BigDecimal", new BigDecimal(1234567890.123), "1234567890.1229999065399169921875"});
        scenarios.add(new Object[] {"BigInteger", new BigInteger("987654321"), "987654321"});

        return scenarios;
    }

    @Before
    public void setUp() {
        mockLoggable = mock(Loggable.class);
        when(mockLoggable.value()).thenReturn("");
    }

    @Test
    public void shouldFormatNumberWithoutAnnotationName() {
        AnnotatedObject annotatedObject = new AnnotatedObject(testNumber, mockLoggable);

        assertThat("Formatted string", testee.format(annotatedObject), is(expectedOutput));
    }

    @Test
    public void shouldFormatNumberWithAnnotationName() {
        when(mockLoggable.value()).thenReturn("name");

        AnnotatedObject annotatedObject = new AnnotatedObject(testNumber, mockLoggable);

        assertThat("Formatted string", testee.format(annotatedObject), is("name=" + expectedOutput));
    }

    @Test
    public void shouldFormatNullNumber() {
        Number testNumber = null;

        AnnotatedObject annotatedObject = new AnnotatedObject(testNumber, mockLoggable);

        assertThat("", testee.format(annotatedObject), is("null"));
    }
}
