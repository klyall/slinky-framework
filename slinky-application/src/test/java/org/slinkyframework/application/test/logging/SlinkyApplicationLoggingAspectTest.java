package org.slinkyframework.application.test.logging;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.Appender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;
import org.slinkyframework.application.test.example.ExampleApplication;
import org.slinkyframework.application.test.example.ExampleApplicationImpl;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.slinkyframework.application.test.example.ExampleApplicationImpl.FORCED_EXCEPTION_ACCOUNT;
import static org.slinkyframework.common.logging.matchers.LoggingMatchers.hasLogMessage;
import static org.slinkyframework.common.logging.matchers.LoggingMatchers.matchesPattern;

@RunWith(MockitoJUnitRunner.class)
public class SlinkyApplicationLoggingAspectTest {

    private static final String TEST_ACCOUNT = "12345";

    @Mock
    Appender mockAppender;

    @Before
    public void setUp() {
        Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.addAppender(mockAppender);
    }

    @Test
    public void shouldLogBeforeAndAfterApplicationCall() {
        String expectedRequestMessage = "----> ExampleApplication retrieveAccountDetails request received []";
        String expectedResponseMessage = "<---- ExampleApplication retrieveAccountDetails response returned in \\[\\d+\\] ms. \\[\\]";

        ExampleApplication testee = new ExampleApplicationImpl();
        testee.retrieveAccountDetails(TEST_ACCOUNT);

        verify(mockAppender, times(2)).doAppend(any());
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(equalTo(expectedRequestMessage))));
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(matchesPattern(expectedResponseMessage))));
    }

    @Test
    public void shouldLogBeforeAndAfterApplicationCallThatThrowExceptions() {
        String expectedRequestMessage = "----> ExampleApplication deleteAccount request received []";
        String expectedResponseMessage = "<---- ExampleApplication deleteAccount exception returned in \\[\\d+\\] ms., exception message \\[.*\\]";

        try {
            ExampleApplication testee = new ExampleApplicationImpl();
            testee.deleteAccount(FORCED_EXCEPTION_ACCOUNT);
        } catch (IllegalArgumentException e) {
            // Ignore as we want to do the verifies below
        }

        verify(mockAppender, times(2)).doAppend(any());
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(equalTo(expectedRequestMessage))));
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(matchesPattern(expectedResponseMessage))));
    }

    @Test
    public void shouldLogBeforeAndAfterFirstApplicationCall() {
        String expectedRequestMessage = "----> ExampleApplication firstMethod request received []";
        String expectedResponseMessage = "<---- ExampleApplication firstMethod response returned in \\[\\d+\\] ms. \\[\\]";

        try {
            ExampleApplication testee = new ExampleApplicationImpl();
            testee.firstMethod();
        } catch (IllegalArgumentException e) {
            // Ignore as we want to do the verifies below
        }

        verify(mockAppender, times(2)).doAppend(any());
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(equalTo(expectedRequestMessage))));
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(matchesPattern(expectedResponseMessage))));
    }
}