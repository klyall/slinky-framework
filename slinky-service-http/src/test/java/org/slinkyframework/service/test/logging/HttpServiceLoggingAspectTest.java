package org.slinkyframework.service.test.logging;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.Appender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slinkyframework.service.test.example.ExampleRestController;
import org.slf4j.LoggerFactory;

import static org.mockito.Mockito.*;
import static org.slinkyframework.common.logging.test.LoggingMatchers.hasLogMessage;

@RunWith(MockitoJUnitRunner.class)
public class HttpServiceLoggingAspectTest {

    @Mock Appender mockAppender;

    @Before
    public void setUp() {
        Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        when(mockAppender.getName()).thenReturn("MOCK");
        root.addAppender(mockAppender);
    }

    @Test
    public void shouldLogBeforeAndAfterServiceEndpoints() {
        String expectedRequestMessage = "--> ExampleRestController publicEndpoint request received";
        String expectedResponseMessage = "<-- ExampleRestController publicEndpoint response returned in \\[\\d+\\] ms.";

        ExampleRestController testee = new ExampleRestController();
        testee.publicEndpoint();

        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(expectedRequestMessage)));
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(expectedResponseMessage)));
    }

    @Test
    public void shouldLogBeforeAndAfterServiceEndpointsThatThrowExceptions() {
        String expectedRequestMessage = "--> ExampleRestController exceptionMethod request received";
        String expectedResponseMessage = "<-- ExampleRestController exceptionMethod exception returned in \\[\\d+\\] ms., exception message \\[.*\\]";

        try {
            ExampleRestController testee = new ExampleRestController();
            testee.exceptionMethod();
        } catch (IllegalArgumentException e) {
            // Ignore as we want to do the verifies below
        }

        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(expectedRequestMessage)));
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(expectedResponseMessage)));
    }
}
