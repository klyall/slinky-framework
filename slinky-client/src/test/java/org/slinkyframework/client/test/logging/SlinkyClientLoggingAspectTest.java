package org.slinkyframework.client.test.logging;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.Appender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;
import org.slinkyframework.client.test.example.ExampleClient;
import org.slinkyframework.client.test.example.ExampleClientImpl;
import org.slinkyframework.client.test.example.domain.Account;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.slinkyframework.common.logging.matchers.LoggingMatchers.hasLogMessage;
import static org.slinkyframework.common.logging.matchers.LoggingMatchers.matchesPattern;

@RunWith(MockitoJUnitRunner.class)
public class SlinkyClientLoggingAspectTest {

    private static final String TEST_ACCOUNT = "12345";

    @Mock
    Appender mockAppender;

    @Before
    public void setUp() {
        Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.addAppender(mockAppender);
    }

    @Test
    public void shouldLogBeforeAndAfterClientCalls() {
        String expectedRequestMessage = "------> ExampleClient updateAccountDetails request sent []";
        String expectedResponseMessage = "<------ ExampleClient updateAccountDetails response received in \\[\\d+\\] ms. \\[\\]";

        ExampleClient testee = new ExampleClientImpl();
        testee.updateAccountDetails(new Account());

        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(equalTo(expectedRequestMessage))));
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(matchesPattern(expectedResponseMessage))));
    }

    @Test
    public void shouldLogBeforeAndAfterClientCallsIncludingLoggableParameters() {
        String expectedRequestMessage = String.format("------> ExampleClient retrieveAccountDetails request sent ['%s']", TEST_ACCOUNT);
        String expectedResponseMessage = "<------ ExampleClient retrieveAccountDetails response received in \\[\\d+\\] ms. \\[\\]";

        ExampleClient testee = new ExampleClientImpl();
        testee.retrieveAccountDetails(TEST_ACCOUNT);

        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(equalTo(expectedRequestMessage))));
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(matchesPattern(expectedResponseMessage))));
    }

    @Test
    public void shouldLogBeforeAndAfterClientCallsThatThrowExceptions() {
        String expectedRequestMessage = "------> ExampleClient deleteAccount request sent []";
        String expectedResponseMessage = "<------ ExampleClient deleteAccount exception received in \\[\\d+\\] ms., exception message \\[.*\\]";

        try {
            ExampleClient testee = new ExampleClientImpl();
            testee.deleteAccount(ExampleClientImpl.FORCED_EXCEPTION_ACCOUNT);
        } catch (IllegalArgumentException e) {
            // Ignore as we want to do the verifies below
        }

        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(equalTo(expectedRequestMessage))));
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(matchesPattern(expectedResponseMessage))));
    }
}