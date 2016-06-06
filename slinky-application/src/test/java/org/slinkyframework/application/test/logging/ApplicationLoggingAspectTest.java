package org.slinkyframework.application.test.logging;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.Appender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slinkyframework.application.test.example.ExampleApplication;
import org.slinkyframework.application.test.example.ExampleApplicationImpl;
import org.slf4j.LoggerFactory;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;
import static org.slinkyframework.application.test.example.ExampleApplicationImpl.FORCED_EXCEPTION_ACCOUNT;
import static org.slinkyframework.common.logging.test.LoggingMatchers.hasLogMessage;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationLoggingAspectTest {

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
    public void shouldLogBeforeAndAfterServiceEndpoints() {
        String expectedRequestMessage = "---> ExampleApplication retrieveAccountDetails request received";
        String expectedResponseMessage = "<--- ExampleApplication retrieveAccountDetails response returned in \\[\\d+\\] ms.";

        ExampleApplication testee = new ExampleApplicationImpl();
        testee.retrieveAccountDetails(TEST_ACCOUNT);

        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(expectedRequestMessage)));
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(expectedResponseMessage)));
    }

    @Test
    public void shouldLogBeforeAndAfterServiceEndpointsThatThrowExceptions() {
        String expectedRequestMessage = "---> ExampleApplication deleteAccount request received";
        String expectedResponseMessage = "<--- ExampleApplication deleteAccount exception returned in \\[\\d+\\] ms., exception message \\[.*\\]";

        try {
            ExampleApplication testee = new ExampleApplicationImpl();
            testee.deleteAccount(FORCED_EXCEPTION_ACCOUNT);
        } catch (IllegalArgumentException e) {
            // Ignore as we want to do the verifies below
        }

        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(expectedRequestMessage)));
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(expectedResponseMessage)));
    }
}