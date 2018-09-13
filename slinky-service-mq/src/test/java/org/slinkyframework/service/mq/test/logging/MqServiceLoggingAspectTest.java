package org.slinkyframework.service.mq.test.logging;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.Appender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;
import org.slinkyframework.service.mq.test.example.ExampleMqService;
import org.slinkyframework.service.mq.test.example.ExampleRequest;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;
import static org.slinkyframework.common.logging.matchers.LoggingMatchers.hasLogMessage;
import static org.slinkyframework.common.logging.matchers.LoggingMatchers.matchesPattern;

@RunWith(MockitoJUnitRunner.class)
public class MqServiceLoggingAspectTest {

    @Mock Appender mockAppender;

    @Before
    public void setUp() {
        Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.addAppender(mockAppender);
    }

    @Test
    public void shouldLogBeforeAndAfterServiceEndpoints() {
        String expectedRequestMessage = "--> ExampleMqService processMessage request received []";
        String expectedResponseMessage = "<-- ExampleMqService processMessage response returned in \\[\\d+\\] ms. \\[\\]";

        ExampleMqService testee = new ExampleMqService();
        testee.processMessage(new ExampleRequest());

        verify(mockAppender, times(2)).doAppend(any());
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(equalTo(expectedRequestMessage))));
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(matchesPattern(expectedResponseMessage))));
    }

    @Test
    public void shouldLogBeforeAndAfterServiceEndpointsThatThrowExceptions() {
        String expectedRequestMessage = "--> ExampleMqService processException request received []";
        String expectedResponseMessage = "<-- ExampleMqService processException exception returned in \\[\\d+\\] ms., exception message \\[.*\\]";

        try {
            ExampleMqService testee = new ExampleMqService();
            testee.processException(new ExampleRequest());
        } catch (IllegalArgumentException e) {
            // Ignore as we want to do the verifies below
        }

        verify(mockAppender, times(2)).doAppend(any());
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(equalTo(expectedRequestMessage))));
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(matchesPattern(expectedResponseMessage))));
    }
}
