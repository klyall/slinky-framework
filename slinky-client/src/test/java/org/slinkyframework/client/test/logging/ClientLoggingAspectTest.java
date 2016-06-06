package org.slinkyframework.client.test.logging;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.Appender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slinkyframework.client.test.example.ExampleClient;
import org.slinkyframework.client.test.example.ExampleClientImpl;
import org.slf4j.LoggerFactory;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.slinkyframework.common.logging.test.LoggingMatchers.hasLogMessage;

@RunWith(MockitoJUnitRunner.class)
public class ClientLoggingAspectTest {

    private static final String TEST_ACCOUNT = "12345";

    @Mock
    Appender mockAppender;

    @Before
    public void setUp() {
        Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        when(mockAppender.getName()).thenReturn("MOCK");
        root.addAppender(mockAppender);
    }

    @Test
    public void shouldLogBeforeAndAfterClientCalls() {
        String expectedRequestMessage = "----> ExampleClient retrieveAccountDetails request sent";
        String expectedResponseMessage = "<---- ExampleClient retrieveAccountDetails response received in \\[\\d+\\] ms.";

        ExampleClient testee = new ExampleClientImpl();
        testee.retrieveAccountDetails(TEST_ACCOUNT);

        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(expectedRequestMessage)));
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(expectedResponseMessage)));
    }

    @Test
    public void shouldLogBeforeAndAfterClientCallsThatThrowExceptions() {
        String expectedRequestMessage = "----> ExampleClient deleteAccount request sent";
        String expectedResponseMessage = "<---- ExampleClient deleteAccount exception received in \\[\\d+\\] ms., exception message \\[.*\\]";

        try {
            ExampleClient testee = new ExampleClientImpl();
            testee.deleteAccount(ExampleClientImpl.FORCED_EXCEPTION_ACCOUNT);
        } catch (IllegalArgumentException e) {
            // Ignore as we want to do the verifies below
        }

        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(expectedRequestMessage)));
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(expectedResponseMessage)));
    }
}