package org.slinkyframework.client.test.logging;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.Appender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slinkyframework.client.test.example.ExampleRepositoryImpl;
import org.slinkyframework.client.test.example.ExampleRepository;
import org.slf4j.LoggerFactory;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.slinkyframework.common.logging.test.LoggingMatchers.hasLogMessage;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryLoggingAspectTest {

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
    public void shouldLogBeforeAndAfterRepositoryCalls() {
        String expectedRequestMessage = "------> ExampleRepository retrieveAccountDetails query sent";
        String expectedResponseMessage = "<------ ExampleRepository retrieveAccountDetails result received in \\[\\d+\\] ms.";

        ExampleRepository testee = new ExampleRepositoryImpl();
        testee.retrieveAccountDetails(TEST_ACCOUNT);

        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(expectedRequestMessage)));
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(expectedResponseMessage)));
    }

    @Test
    public void shouldLogBeforeAndAfterRepositoryCallsThatThrowExceptions() {
        String expectedRequestMessage = "------> ExampleRepository deleteAccount query sent";
        String expectedResponseMessage = "<------ ExampleRepository deleteAccount exception received in \\[\\d+\\] ms., exception message \\[.*\\]";

        try {
            ExampleRepository testee = new ExampleRepositoryImpl();
            testee.deleteAccount(ExampleRepositoryImpl.FORCED_EXCEPTION_ACCOUNT);
        } catch (IllegalArgumentException e) {
            // Ignore as we want to do the verifies below
        }

        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(expectedRequestMessage)));
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(expectedResponseMessage)));
    }
}