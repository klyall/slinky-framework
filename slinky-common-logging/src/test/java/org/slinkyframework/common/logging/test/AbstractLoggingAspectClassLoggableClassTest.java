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

import static java.lang.String.format;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;
import static org.slinkyframework.common.logging.matchers.LoggingMatchers.hasLogMessage;
import static org.slinkyframework.common.logging.matchers.LoggingMatchers.matchesPattern;

@RunWith(MockitoJUnitRunner.class)
public class AbstractLoggingAspectClassLoggableClassTest {

    @Mock
    Appender mockAppender;

    @Before
    public void setUp() {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.addAppender(mockAppender);
    }

    @Test
    public void shouldLogAnonymousLoggableParametersThatAreClasses() {
        String expectedBeforeMessage = "Before - ClassName: ExampleClass - MethodName: hasAnonymousLoggableClassParameters - Arguments: [(id=1, name=(firstName='Joe'))]";
        String expectedAfterMessage = "After - ClassName: ExampleClass - MethodName: hasAnonymousLoggableClassParameters - Response time: \\[\\d+\\] ms \\[\\]";

        ExampleClass exampleClass = new ExampleClass();
        exampleClass.hasAnonymousLoggableClassParameters(new ExamplePerson(1, new ExampleName("Joe", "Bloggs")));

        verifyLogStatements(expectedBeforeMessage, expectedAfterMessage);
    }

    @Test
    public void shouldLogNamedLoggableParametersThatAreClasses() {
        String expectedBeforeMessage = "Before - ClassName: ExampleClass - MethodName: hasNamedLoggableClassParameters - Arguments: [person=(id=1, name=(firstName='Joe'))]";
        String expectedAfterMessage = "After - ClassName: ExampleClass - MethodName: hasNamedLoggableClassParameters - Response time: \\[\\d+\\] ms \\[\\]";

        ExampleClass exampleClass = new ExampleClass();
        exampleClass.hasNamedLoggableClassParameters(new ExamplePerson(1, new ExampleName("Joe", "Bloggs")));

        verifyLogStatements(expectedBeforeMessage, expectedAfterMessage);
    }

    @Test
    public void shouldLogClassParameterThatIsNull() {
        String expectedBeforeMessage = "Before - ClassName: ExampleClass - MethodName: hasAnonymouLoggableClassWithAllTypes - Arguments: [null]";
        String expectedAfterMessage = "After - ClassName: ExampleClass - MethodName: hasAnonymouLoggableClassWithAllTypes - Response time: \\[\\d+\\] ms \\[\\]";

        ExampleClass exampleClass = new ExampleClass();
        exampleClass.hasAnonymouLoggableClassWithAllTypes(null);

        verifyLogStatements(expectedBeforeMessage, expectedAfterMessage);
    }

    @Test
    public void shouldLogClassParameterThatHasAllMemberVariablesOfAllTypesNull() {
        String expectedBeforeMessage = "Before - ClassName: ExampleClass - MethodName: hasAnonymouLoggableClassWithAllTypes - Arguments: [(string=null, integer=null, aShort=null, aLong=null, aFloat=null, date=null, aDouble=null, aByte=null, character=null, aBoolean=null, localDate=null, localDateTime=null, localTime=null, exampleEnum=null, collection=null)]";
        String expectedAfterMessage = "After - ClassName: ExampleClass - MethodName: hasAnonymouLoggableClassWithAllTypes - Response time: \\[\\d+\\] ms \\[\\]";

        ExampleClass exampleClass = new ExampleClass();
        exampleClass.hasAnonymouLoggableClassWithAllTypes(new AllTypes());

        verifyLogStatements(expectedBeforeMessage, expectedAfterMessage);
    }

    @Test
    public void shouldLogClassParameterThatHasAllMemberVariablesOfAllTypesPopulated() {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        Date date = new Date();

        String expectedBeforeMessage = format("Before - ClassName: ExampleClass - MethodName: hasAnonymouLoggableClassWithAllTypes - Arguments: [(string='String', integer=4, aShort=32767, aLong=4, aFloat=3.0, date=%s, aDouble=2.0, aByte=127, character='a', aBoolean=true, localDate=%s, localDateTime=%s, localTime=%s, exampleEnum=ONE, collection=(size=0))]", toLocalDateTime(date), localDate, localDateTime, localTime);
        String expectedAfterMessage = "After - ClassName: ExampleClass - MethodName: hasAnonymouLoggableClassWithAllTypes - Response time: \\[\\d+\\] ms \\[\\]";

        AllTypes allTypes = new AllTypes();
        allTypes.setaBoolean(true);
        allTypes.setaByte(Byte.MAX_VALUE);
        allTypes.setaDouble(2.0d);
        allTypes.setaFloat(3.0f);
        allTypes.setaLong(4L);
        allTypes.setaShort(Short.MAX_VALUE);
        allTypes.setCharacter('a');
        allTypes.setCollection(new ArrayList());
        allTypes.setDate(date);
        allTypes.setExampleEnum(ExampleEnum.ONE);
        allTypes.setInteger(4);
        allTypes.setLocalDate(localDate);
        allTypes.setLocalDateTime(localDateTime);
        allTypes.setLocalTime(localTime);
        allTypes.setString("String");

        ExampleClass exampleClass = new ExampleClass();
        exampleClass.hasAnonymouLoggableClassWithAllTypes(allTypes);

        verifyLogStatements(expectedBeforeMessage, expectedAfterMessage);
    }


    @Test
    public void shouldNotDisplayClassVariablesThatAreNotExternallyVisible() {
        String expectedBeforeMessage = "Before - ClassName: ExampleClass - MethodName: hasAnonymouLoggableClassWithoutAccessors - Arguments: [(string1='String1', string2='NOT_VISIBLE', string3='NOT_VISIBLE')]";
        String expectedAfterMessage = "After - ClassName: ExampleClass - MethodName: hasAnonymouLoggableClassWithoutAccessors - Response time: \\[\\d+\\] ms \\[\\]";

        ExampleClass exampleClass = new ExampleClass();
        exampleClass.hasAnonymouLoggableClassWithoutAccessors(new ClassWithoutAccessor("String1", "String2", "String3"));

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
