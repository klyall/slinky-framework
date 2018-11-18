package org.slinkyframework.common.logging.formatters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slinkyframework.common.aop.domain.AnnotatedObject;
import org.slinkyframework.common.logging.Loggable;
import org.slinkyframework.common.logging.test.example.ExampleEnum;
import org.slinkyframework.common.logging.test.example.ExampleName;
import org.slinkyframework.common.logging.test.example.ExamplePerson;

import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.util.*;

import static java.lang.String.format;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class LoggableFormatterTest {

    private final Object testObject;
    private final String expected;
    private Loggable mockLoggable;

    private ParameterFormatter testee = new ParameterFormatter();

    public LoggableFormatterTest(String scenario, Object testObject, String expected) {
        this.testObject = testObject;
        this.expected = expected;
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> createScenarios() throws Exception {
        List<Object[]> scenarios = new ArrayList<>();

        scenarios.add(new Object[] { "Boolean", Boolean.TRUE, "true" });
        scenarios.add(new Object[] { "Byte", new Byte("100"), "100" });
        scenarios.add(new Object[] { "Character", new Character('a'), "'a'" });
        scenarios.add(new Object[] { "Collection", Arrays.asList(1, 2, 3), "(size=3)" });
        scenarios.add(new Object[] { "Date", Date.from(LocalDateTime.of(2018, 01, 31, 17, 18, 19).atZone(ZoneId.systemDefault()).toInstant()), "2018-01-31T17:18:19" });
        scenarios.add(new Object[] { "Double", Double.valueOf("25.5"), "25.5" });
        scenarios.add(new Object[] { "Enum", ExampleEnum.ONE, "ONE" });
        scenarios.add(new Object[] { "Float", 20.0, "20.0" });
        scenarios.add(new Object[] { "Integer", 5, "5" });
        scenarios.add(new Object[] { "LocalDate", LocalDate.of(2018, 03, 31), "2018-03-31" });
        scenarios.add(new Object[] { "LocalDateTime", LocalDateTime.of(2018, 01, 31, 17, 18, 19), "2018-01-31T17:18:19" });
        scenarios.add(new Object[] { "LocalTime", LocalTime.of(13, 12, 11), "13:12:11" });
        scenarios.add(new Object[] { "Long", 15L, "15" });
        scenarios.add(new Object[] { "Optional(Long)", Optional.of(15L), "Optional(15)" });
        scenarios.add(new Object[] { "Optional(Class)", Optional.of(new ExampleName("Joe", "Bloggs")), "Optional(ExampleName(firstName='Joe'))" });
        scenarios.add(new Object[] { "Optional(String)", Optional.of("Bingo"), "Optional('Bingo')" });
        scenarios.add(new Object[] { "Short", Short.valueOf("10"), "10" });
        scenarios.add(new Object[] { "String", "Bob", "'Bob'" });
        scenarios.add(new Object[] { "Class", new ExampleName("Joe", "Bloggs"), "(firstName='Joe')" });
        scenarios.add(new Object[] { "Nested Class", new ExamplePerson(1, new ExampleName("Bob", "Smith")), "(id=1, name=(firstName='Bob'))" });
        scenarios.add(new Object[] { "URL", new URL("https://dotaekwondo.net"), "https://dotaekwondo.net" });

        LocalDate nowLocalDate = LocalDate.now();
        LocalDateTime nowLocalDateTime = LocalDateTime.now();
        ZonedDateTime nowZonedDateTime = ZonedDateTime.now();

        scenarios.add(new Object[] { "Calendar", GregorianCalendar.from(nowZonedDateTime), nowZonedDateTime.toString() });
        scenarios.add(new Object[] { "sql.Date", java.sql.Date.valueOf(nowLocalDate), nowLocalDate.toString() });
        scenarios.add(new Object[] { "sql.Timestamp", Timestamp.valueOf(nowLocalDateTime), nowLocalDateTime.toString() });

        return scenarios;
    }

    @Before
    public void setUp() {
        mockLoggable = mock(Loggable.class);
    }

    @Test
    public void shouldExtractAnonymousLoggableCoreClasses() {
        setupAnonymousLoggableMock();

        assertThat("Anonymous parameter", testFormat(testObject),  is(format("[%s]",  expected)));
    }

    private void setupAnonymousLoggableMock() {
        when(mockLoggable.value()).thenReturn("");
    }

    private String testFormat(Object object) {
        AnnotatedObject annotatedObject = new AnnotatedObject(object, mockLoggable);

        return testee.format(annotatedObject);
    }

    @Test
    public void shouldExtractNamedLoggableCoreClasses() {
        setupNamedLoggableMock();

        assertThat("Anonymous parameter", testFormat(testObject), is(format("[Name=%s]",  expected)));
    }

    private void setupNamedLoggableMock() {
        when(mockLoggable.value()).thenReturn("Name");
    }
}
