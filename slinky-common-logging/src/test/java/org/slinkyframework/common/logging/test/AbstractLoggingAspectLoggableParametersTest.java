package org.slinkyframework.common.logging.test;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.Appender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;
import org.slinkyframework.common.logging.test.example.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.String.format;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;
import static org.slinkyframework.common.logging.matchers.LoggingMatchers.hasLogMessage;
import static org.slinkyframework.common.logging.matchers.LoggingMatchers.matchesPattern;

@RunWith(MockitoJUnitRunner.class)
public class AbstractLoggingAspectLoggableParametersTest {

    @Mock
    Appender mockAppender;

    @Before
    public void setUp() {
        Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.addAppender(mockAppender);
    }

    @Test
    public void shouldLogBeforeAndAfterMethodCalls() {
        String expectedBeforeMessage = "Before - ClassName: ExampleClass - MethodName: hasNoParameters - Arguments: []";
        String expectedAfterMessage = "After - ClassName: ExampleClass - MethodName: hasNoParameters - Response time: \\[\\d+\\] ms \\[\\]";

        ExampleClass exampleClass = new ExampleClass();
        exampleClass.hasNoParameters();

        verifyLogStatements(expectedBeforeMessage, expectedAfterMessage);
    }

    @Test
    public void shouldLogBeforeAndAfterMethodCallsWithParametersThatAreNotLoggable() {
        String expectedBeforeMessage = "Before - ClassName: ExampleClass - MethodName: hasNoLoggableParameter - Arguments: []";
        String expectedAfterMessage = "After - ClassName: ExampleClass - MethodName: hasNoLoggableParameter - Response time: \\[\\d+\\] ms \\[\\]";

        ExampleClass exampleClass = new ExampleClass();
        exampleClass.hasNoLoggableParameter("Mr");

        verifyLogStatements(expectedBeforeMessage, expectedAfterMessage);
    }

    @Test
    public void shouldLogAnonymousLoggableParameters() {
        String expectedBeforeMessage = "Before - ClassName: ExampleClass - MethodName: hasAnonymousLoggableParameters - Arguments: ['String', 1]";
        String expectedAfterMessage = "After - ClassName: ExampleClass - MethodName: hasAnonymousLoggableParameters - Response time: \\[\\d+\\] ms \\[\\]";

        ExampleClass exampleClass = new ExampleClass();
        exampleClass.hasAnonymousLoggableParameters("String", 1);

        verifyLogStatements(expectedBeforeMessage, expectedAfterMessage);
    }

    @Test
    public void shouldLogNamedLoggableParameters() {
        String expectedBeforeMessage = "Before - ClassName: ExampleClass - MethodName: hasNamedLoggableParameters - Arguments: [stringParam='String', intParam=1, booleanParam=true]";
        String expectedAfterMessage = "After - ClassName: ExampleClass - MethodName: hasNamedLoggableParameters - Response time: \\[\\d+\\] ms \\[\\]";

        ExampleClass exampleClass = new ExampleClass();
        exampleClass.hasNamedLoggableParameters("String", 1, true);

        verifyLogStatements(expectedBeforeMessage, expectedAfterMessage);
    }

    @Test
    public void shouldLogLoggableStringParameters() {
        String expectedBeforeMessage = "Before - ClassName: ExampleClass - MethodName: hasAnonymousLoggableParameter - Arguments: ['string', null]";
        String expectedAfterMessage = "After - ClassName: ExampleClass - MethodName: hasAnonymousLoggableParameter - Response time: \\[\\d+\\] ms \\[\\]";

        ExampleClass exampleClass = new ExampleClass();
        exampleClass.hasAnonymousLoggableParameter("string", null);

        verifyLogStatements(expectedBeforeMessage, expectedAfterMessage);
    }

    @Test
    public void shouldLogLoggableShortParameters() {
        String expectedBeforeMessage = "Before - ClassName: ExampleClass - MethodName: hasAnonymousLoggableParameter - Arguments: [1, null]";
        String expectedAfterMessage = "After - ClassName: ExampleClass - MethodName: hasAnonymousLoggableParameter - Response time: \\[\\d+\\] ms \\[\\]";

        ExampleClass exampleClass = new ExampleClass();
        exampleClass.hasAnonymousLoggableParameter(new Short("1"), null);

        verifyLogStatements(expectedBeforeMessage, expectedAfterMessage);
    }

    @Test
    public void shouldLogLoggableIntParameters() {
        String expectedBeforeMessage = "Before - ClassName: ExampleClass - MethodName: hasAnonymousLoggableParameter - Arguments: [1, null]";
        String expectedAfterMessage = "After - ClassName: ExampleClass - MethodName: hasAnonymousLoggableParameter - Response time: \\[\\d+\\] ms \\[\\]";

        ExampleClass exampleClass = new ExampleClass();
        exampleClass.hasAnonymousLoggableParameter(1, null);

        verifyLogStatements(expectedBeforeMessage, expectedAfterMessage);
    }

    @Test
    public void shouldLogLoggableLongParameters() {
        String expectedBeforeMessage = "Before - ClassName: ExampleClass - MethodName: hasAnonymousLoggableParameter - Arguments: [1, null]";
        String expectedAfterMessage = "After - ClassName: ExampleClass - MethodName: hasAnonymousLoggableParameter - Response time: \\[\\d+\\] ms \\[\\]";

        ExampleClass exampleClass = new ExampleClass();
        exampleClass.hasAnonymousLoggableParameter(1L, null);

        verifyLogStatements(expectedBeforeMessage, expectedAfterMessage);
    }

    @Test
    public void shouldLogLoggableFloatParameters() {
        String expectedBeforeMessage = "Before - ClassName: ExampleClass - MethodName: hasAnonymousLoggableParameter - Arguments: [1.0, null]";
        String expectedAfterMessage = "After - ClassName: ExampleClass - MethodName: hasAnonymousLoggableParameter - Response time: \\[\\d+\\] ms \\[\\]";

        ExampleClass exampleClass = new ExampleClass();
        exampleClass.hasAnonymousLoggableParameter(1f, null);

        verifyLogStatements(expectedBeforeMessage, expectedAfterMessage);
    }

    @Test
    public void shouldLogLoggableDoubleParameters() {
        String expectedBeforeMessage = "Before - ClassName: ExampleClass - MethodName: hasAnonymousLoggableParameter - Arguments: [1.0, null]";
        String expectedAfterMessage = "After - ClassName: ExampleClass - MethodName: hasAnonymousLoggableParameter - Response time: \\[\\d+\\] ms \\[\\]";

        ExampleClass exampleClass = new ExampleClass();
        exampleClass.hasAnonymousLoggableParameter(1d, null);

        verifyLogStatements(expectedBeforeMessage, expectedAfterMessage);
    }

    @Test
    public void shouldLogLoggableByteParameters() {
        String expectedBeforeMessage = "Before - ClassName: ExampleClass - MethodName: hasAnonymousLoggableParameter - Arguments: [1, null]";
        String expectedAfterMessage = "After - ClassName: ExampleClass - MethodName: hasAnonymousLoggableParameter - Response time: \\[\\d+\\] ms \\[\\]";

        ExampleClass exampleClass = new ExampleClass();
        exampleClass.hasAnonymousLoggableParameter(new Byte("1"), null);

        verifyLogStatements(expectedBeforeMessage, expectedAfterMessage);
    }

    @Test
    public void shouldLogLoggableCharParameters() {
        String expectedBeforeMessage = "Before - ClassName: ExampleClass - MethodName: hasAnonymousLoggableParameter - Arguments: ['a', null]";
        String expectedAfterMessage = "After - ClassName: ExampleClass - MethodName: hasAnonymousLoggableParameter - Response time: \\[\\d+\\] ms \\[\\]";

        ExampleClass exampleClass = new ExampleClass();
        exampleClass.hasAnonymousLoggableParameter('a', null);

        verifyLogStatements(expectedBeforeMessage, expectedAfterMessage);
    }

    @Test
    public void shouldLogLoggableEnumParameters() {
        String expectedBeforeMessage = "Before - ClassName: ExampleClass - MethodName: hasAnonymousLoggableParameter - Arguments: [ONE, null]";
        String expectedAfterMessage = "After - ClassName: ExampleClass - MethodName: hasAnonymousLoggableParameter - Response time: \\[\\d+\\] ms \\[\\]";

        ExampleClass exampleClass = new ExampleClass();
        exampleClass.hasAnonymousLoggableParameter(ExampleEnum.ONE, null);

        verifyLogStatements(expectedBeforeMessage, expectedAfterMessage);
    }

    @Test
    public void shouldLogLoggableBooleanParameters() {
        String expectedBeforeMessage = "Before - ClassName: ExampleClass - MethodName: hasAnonymousLoggableParameter - Arguments: [true, null]";
        String expectedAfterMessage = "After - ClassName: ExampleClass - MethodName: hasAnonymousLoggableParameter - Response time: \\[\\d+\\] ms \\[\\]";

        ExampleClass exampleClass = new ExampleClass();
        exampleClass.hasAnonymousLoggableParameter(true, null);

        verifyLogStatements(expectedBeforeMessage, expectedAfterMessage);
    }

    @Test
    public void shouldLogLoggableDateParameters() {
        Date param = new Date();

        String expectedBeforeMessage = format("Before - ClassName: ExampleClass - MethodName: hasAnonymousLoggableParameter - Arguments: [%s, null]", toLocalDateTime(param));
        String expectedAfterMessage = "After - ClassName: ExampleClass - MethodName: hasAnonymousLoggableParameter - Response time: \\[\\d+\\] ms \\[\\]";

        ExampleClass exampleClass = new ExampleClass();
        exampleClass.hasAnonymousLoggableParameter(param, null);

        verifyLogStatements(expectedBeforeMessage, expectedAfterMessage);
    }

    @Test
    public void shouldLogLoggableLocalDateParameters() {
        LocalDate param = LocalDate.now();

        String expectedBeforeMessage = format("Before - ClassName: ExampleClass - MethodName: hasAnonymousLoggableParameter - Arguments: [%s, null]", param);
        String expectedAfterMessage = "After - ClassName: ExampleClass - MethodName: hasAnonymousLoggableParameter - Response time: \\[\\d+\\] ms \\[\\]";

        ExampleClass exampleClass = new ExampleClass();
        exampleClass.hasAnonymousLoggableParameter(param, null);

        verifyLogStatements(expectedBeforeMessage, expectedAfterMessage);
    }

    @Test
    public void shouldLogLoggableLocalDateTimeParameters() {
        LocalDateTime param = LocalDateTime.now();

        String expectedBeforeMessage = format("Before - ClassName: ExampleClass - MethodName: hasAnonymousLoggableParameter - Arguments: [%s, null]", param);
        String expectedAfterMessage = "After - ClassName: ExampleClass - MethodName: hasAnonymousLoggableParameter - Response time: \\[\\d+\\] ms \\[\\]";

        ExampleClass exampleClass = new ExampleClass();
        exampleClass.hasAnonymousLoggableParameter(param, null);

        verifyLogStatements(expectedBeforeMessage, expectedAfterMessage);
    }

    @Test
    public void shouldLogLoggableLocalTimeParameters() {
        LocalTime param = LocalTime.now();

        String expectedBeforeMessage = format("Before - ClassName: ExampleClass - MethodName: hasAnonymousLoggableParameter - Arguments: [%s, null]", param);
        String expectedAfterMessage = "After - ClassName: ExampleClass - MethodName: hasAnonymousLoggableParameter - Response time: \\[\\d+\\] ms \\[\\]";

        ExampleClass exampleClass = new ExampleClass();
        exampleClass.hasAnonymousLoggableParameter(param, null);

        verifyLogStatements(expectedBeforeMessage, expectedAfterMessage);
    }

    @Test
    public void shouldLogNamedLoggableParametersThatAreCollections() {
        String expectedBeforeMessage = "Before - ClassName: ExampleClass - MethodName: hasNamedLoggableCollectionParameters - Arguments: [items=(size=2)]";
        String expectedAfterMessage = "After - ClassName: ExampleClass - MethodName: hasNamedLoggableCollectionParameters - Response time: \\[\\d+\\] ms \\[\\]";

        List<String> items = new ArrayList<>();
        items.add("one");
        items.add("two");

        ExampleClass exampleClass = new ExampleClass();
        exampleClass.hasNamedLoggableCollectionParameters(items);

        verifyLogStatements(expectedBeforeMessage, expectedAfterMessage);
    }

    private void verifyLogStatements(String expectedBeforeMessage, String expectedAfterMessage) {
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(equalTo(expectedBeforeMessage))));
        verify(mockAppender, times(1)).doAppend(argThat(hasLogMessage(matchesPattern(expectedAfterMessage))));
    }

    private LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
