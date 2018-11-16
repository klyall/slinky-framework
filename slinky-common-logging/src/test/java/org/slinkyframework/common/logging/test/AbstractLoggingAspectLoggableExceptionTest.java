package org.slinkyframework.common.logging.test;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.Appender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;
import org.slinkyframework.common.logging.test.example.ExampleClass;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;
import static org.slinkyframework.common.logging.matchers.LoggingMatchers.hasLogMessage;
import static org.slinkyframework.common.logging.matchers.LoggingMatchers.matchesPattern;

@RunWith(MockitoJUnitRunner.class)
public class AbstractLoggingAspectLoggableExceptionTest {

    @Mock
    Appender mockAppender;

    @Before
    public void setUp() {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.addAppender(mockAppender);
    }

    @Test
    public void shouldLogAnonymousResponse() {
        String expectedBeforeMessage = "Before - ClassName: ExampleClass - MethodName: hasException - Arguments: []";
        String expectedAfterMessage = "After - ClassName: ExampleClass - MethodName: hasException - Returned exception 'Forced exception' in \\[\\d+\\] ms";

        ExampleClass exampleClass = new ExampleClass();
        try {
            exampleClass.hasException();
        } catch (IllegalArgumentException e) {
            // Ignore -forced exception
        }

        verifyLogStatements(expectedBeforeMessage, expectedAfterMessage);
    }

    private void verifyLogStatements(String expectedBeforeMessage, String expectedAfterMessage) {
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(equalTo(expectedBeforeMessage))));
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(matchesPattern(expectedAfterMessage))));
    }



}
