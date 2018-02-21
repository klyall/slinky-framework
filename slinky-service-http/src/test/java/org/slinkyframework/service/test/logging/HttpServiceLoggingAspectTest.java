package org.slinkyframework.service.test.logging;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.Appender;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slinkyframework.service.test.example.ExampleRestController;
import org.slf4j.LoggerFactory;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.slinkyframework.common.logging.matchers.LoggingMatchers.hasLogMessage;
import static org.slinkyframework.common.logging.matchers.LoggingMatchers.matchesPattern;

@RunWith(MockitoJUnitRunner.class)
public class HttpServiceLoggingAspectTest {

    @Mock Appender mockAppender;

    @Before
    public void setUp() {
        Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.addAppender(mockAppender);
    }

    @Test
    public void shouldLogBeforeAndAfterServiceEndpoints() {
        String expectedRequestMessage = "--> ExampleRestController publicEndpoint request received []";
        String expectedResponseMessage = "<-- ExampleRestController publicEndpoint response returned in \\[\\d+\\] ms. \\[\\]";

        ExampleRestController testee = new ExampleRestController();
        testee.publicEndpoint();

        verify(mockAppender, times(2)).doAppend(any());
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(equalTo(expectedRequestMessage))));
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(matchesPattern(expectedResponseMessage))));
    }

    @Test
    public void shouldLogBeforeAndAfterServiceEndpointsThatThrowExceptions() {
        String expectedRequestMessage = "--> ExampleRestController exceptionMethod request received []";
        String expectedResponseMessage = "<-- ExampleRestController exceptionMethod exception returned in \\[\\d+\\] ms., exception message \\[.*\\]";

        try {
            ExampleRestController testee = new ExampleRestController();
            testee.exceptionMethod();
        } catch (IllegalArgumentException e) {
            // Ignore as we want to do the verifies below
        }

        verify(mockAppender, times(2)).doAppend(any());
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(equalTo(expectedRequestMessage))));
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(matchesPattern(expectedResponseMessage))));
    }

    @Test
    @Ignore // Not found the pointcut to avoid this yet
    public void shouldLogBeforeAndAfterFirstServiceEndpoints() {
        String expectedRequestMessage = "--> ExampleRestController publicEndpoint request received []";
        String expectedResponseMessage = "<-- ExampleRestController publicEndpoint response returned in \\[\\d+\\] ms. \\[\\]";

        ExampleRestController testee = new ExampleRestController();
        testee.firstEndpoint();

        verify(mockAppender, times(2)).doAppend(any());
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(equalTo(expectedRequestMessage))));
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(matchesPattern(expectedResponseMessage))));
    }
}
