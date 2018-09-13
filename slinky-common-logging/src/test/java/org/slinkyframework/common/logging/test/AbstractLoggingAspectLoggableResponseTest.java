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
public class AbstractLoggingAspectLoggableResponseTest {

    @Mock
    Appender mockAppender;

    @Before
    public void setUp() {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.addAppender(mockAppender);
    }

    @Test
    public void shouldLogAnonymousResponse() {
        String expectedBeforeMessage = "Before - ClassName: ExampleClass - MethodName: hasAnonymousLoggableResponse - Arguments: []";
        String expectedAfterMessage = "After - ClassName: ExampleClass - MethodName: hasAnonymousLoggableResponse - Response time: \\[\\d+\\] ms \\['ping'\\]";

        ExampleClass exampleClass = new ExampleClass();
        exampleClass.hasAnonymousLoggableResponse("ping");

        verifyLogStatements(expectedBeforeMessage, expectedAfterMessage);
    }

    @Test
    public void shouldLogNamedResponse() {
        String expectedBeforeMessage = "Before - ClassName: ExampleClass - MethodName: hasNamedLoggableResponse - Arguments: []";
        String expectedAfterMessage = "After - ClassName: ExampleClass - MethodName: hasNamedLoggableResponse - Response time: \\[\\d+\\] ms \\[Ping='pong'\\]";

        ExampleClass exampleClass = new ExampleClass();
        exampleClass.hasNamedLoggableResponse("pong");

        verifyLogStatements(expectedBeforeMessage, expectedAfterMessage);
    }

    @Test
    public void shouldLogNullResponse() {
        String expectedBeforeMessage = "Before - ClassName: ExampleClass - MethodName: hasNullLoggableResponse - Arguments: []";
        String expectedAfterMessage = "After - ClassName: ExampleClass - MethodName: hasNullLoggableResponse - Response time: \\[\\d+\\] ms \\[null\\]";

        ExampleClass exampleClass = new ExampleClass();
        exampleClass.hasNullLoggableResponse();

        verifyLogStatements(expectedBeforeMessage, expectedAfterMessage);
    }

    @Test
    public void shouldLogClassResponse() {
        String expectedBeforeMessage = "Before - ClassName: ExampleClass - MethodName: hasNamedClassLoggableResponse - Arguments: []";
        String expectedAfterMessage = "After - ClassName: ExampleClass - MethodName: hasNamedClassLoggableResponse - Response time: \\[\\d+\\] ms \\[Name=\\(firstName='Joe'\\)\\]";

        ExampleClass exampleClass = new ExampleClass();
        exampleClass.hasNamedClassLoggableResponse();

        verifyLogStatements(expectedBeforeMessage, expectedAfterMessage);
    }

    private void verifyLogStatements(String expectedBeforeMessage, String expectedAfterMessage) {
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(equalTo(expectedBeforeMessage))));
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(matchesPattern(expectedAfterMessage))));
    }
}
