package org.slinkyframework.client.test.logging;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.Appender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;
import org.slinkyframework.client.test.example.ExampleRepository;
import org.slinkyframework.client.test.example.ExampleRepositoryImpl;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;
import static org.slinkyframework.common.logging.matchers.LoggingMatchers.hasLogMessage;
import static org.slinkyframework.common.logging.matchers.LoggingMatchers.matchesPattern;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryLoggingAspectTest {

    private static final String TEST_ACCOUNT = "12345";

    @Mock
    Appender mockAppender;

    @Before
    public void setUp() {
        Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.addAppender(mockAppender);
    }

    @Test
    public void shouldLogBeforeAndAfterRepositoryCalls() {
        String expectedRequestMessage = "------> ExampleRepository retrieveAccountDetails query sent []";
        String expectedResponseMessage = "<------ ExampleRepository retrieveAccountDetails result received in \\[\\d+\\] ms. \\[\\]";

        ExampleRepository testee = new ExampleRepositoryImpl();
        testee.retrieveAccountDetails(TEST_ACCOUNT);

        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(equalTo(expectedRequestMessage))));
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(matchesPattern(expectedResponseMessage))));
    }

    @Test
    public void shouldLogBeforeAndAfterRepositoryCallsThatThrowExceptions() {
        String expectedRequestMessage = "------> ExampleRepository deleteAccount query sent []";
        String expectedResponseMessage = "<------ ExampleRepository deleteAccount exception received in \\[\\d+\\] ms., exception message \\[.*\\]";

        try {
            ExampleRepository testee = new ExampleRepositoryImpl();
            testee.deleteAccount(ExampleRepositoryImpl.FORCED_EXCEPTION_ACCOUNT);
        } catch (IllegalArgumentException e) {
            // Ignore as we want to do the verifies below
        }

        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(equalTo(expectedRequestMessage))));
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(matchesPattern(expectedResponseMessage))));
    }
}