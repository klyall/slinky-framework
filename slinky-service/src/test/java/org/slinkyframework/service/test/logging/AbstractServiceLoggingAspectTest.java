package org.slinkyframework.service.test.logging;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.Appender;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;
import org.slinkyframework.service.test.example.ExampleService;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;
import static org.slinkyframework.common.logging.matchers.LoggingMatchers.hasLogMessage;
import static org.slinkyframework.common.logging.matchers.LoggingMatchers.matchesPattern;

@RunWith(MockitoJUnitRunner.class)
public class AbstractServiceLoggingAspectTest {

    @Mock
    Appender mockAppender;

    @Before
    public void setUp() {
        Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.addAppender(mockAppender);
    }

    @Test
    public void shouldLogBeforeAndAfterServiceEndpoints() {
        String expectedRequestMessage = "--> ExampleService publicEndpoint request received []";
        String expectedResponseMessage = "<-- ExampleService publicEndpoint response returned in \\[\\d+\\] ms. \\[\\]";

        ExampleService testee = new ExampleService();
        testee.publicEndpoint();

        verify(mockAppender, times(2)).doAppend(any());
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(equalTo(expectedRequestMessage))));
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(matchesPattern(expectedResponseMessage))));
    }

    @Test
    public void shouldLogBeforeAndAfterServiceEndpointsThatThrowExceptions() {
        String expectedRequestMessage = "--> ExampleService exceptionMethod request received []";
        String expectedResponseMessage = "<-- ExampleService exceptionMethod exception returned in \\[\\d+\\] ms., exception message \\[.*\\]";

        try {
            ExampleService testee = new ExampleService();
            testee.exceptionMethod();
        } catch (IllegalArgumentException e) {
            // Ignore as we want to do the verifies below
        }

        verify(mockAppender, times(2)).doAppend(any());
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(equalTo(expectedRequestMessage))));
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(matchesPattern(expectedResponseMessage))));
    }

    @Test
    @Ignore // Not worked out correct pointcut to use - cflowbelow?
    public void shouldLogBeforeAndAfterFirstServiceEndpoints() {
        String expectedRequestMessage = "--> ExampleService firstEndpoint request received []";
        String expectedResponseMessage = "<-- ExampleService firstEndpoint response returned in \\[\\d+\\] ms. \\[\\]";

        ExampleService testee = new ExampleService();
        testee.firstEndpoint();

        verify(mockAppender, times(2)).doAppend(any());
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(equalTo(expectedRequestMessage))));
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(matchesPattern(expectedResponseMessage))));
    }
}
