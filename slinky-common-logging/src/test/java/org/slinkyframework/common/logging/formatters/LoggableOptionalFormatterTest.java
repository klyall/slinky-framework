package org.slinkyframework.common.logging.formatters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slinkyframework.common.aop.domain.AnnotatedObject;
import org.slinkyframework.common.logging.Loggable;
import org.slinkyframework.common.logging.test.example.ExampleName;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoggableOptionalFormatterTest {

    @Mock
    private Loggable mockLoggable;

    private LoggableFormatterFactory loggableFormatterFactory = new LoggableFormatterFactory();
    private LoggableFormatter testee = new LoggableOptionalFormatter(loggableFormatterFactory);

    @Before
    public void setUp() {
        when(mockLoggable.value()).thenReturn("");
    }

    @Test
    public void shouldFormatOptionalWithoutAnnotationName() {
        Optional testOptional = Optional.of("Bob");

        AnnotatedObject annotatedObject = new AnnotatedObject(testOptional, mockLoggable);

        assertThat("Formatted string", testee.format(annotatedObject), is("Optional('Bob')"));
    }

    @Test
    public void shouldFormatOptionalWithAnnotationName() {
        Optional testOptional = Optional.of("Bob");

        when(mockLoggable.value()).thenReturn("success");

        AnnotatedObject annotatedObject = new AnnotatedObject(testOptional, mockLoggable);

        assertThat("Formatted string", testee.format(annotatedObject), is("success=Optional('Bob')"));
    }

    @Test
    public void shouldFormatEmptyOptionalWithoutAnnotationName() {
        Optional testOptional = Optional.empty();

        AnnotatedObject annotatedObject = new AnnotatedObject(testOptional, mockLoggable);

        assertThat("Empty optional", testee.format(annotatedObject), is("Optional(EMPTY)"));
    }

    @Test
    public void shouldFormatOptionalLoggableClass() {
        Optional testOptional = Optional.of(new ExampleName("Joe", "Bloggs"));

        AnnotatedObject annotatedObject = new AnnotatedObject(testOptional, mockLoggable);

        assertThat("Empty optional", testee.format(annotatedObject), is("Optional(ExampleName(firstName='Joe'))"));
    }

    @Test
    public void shouldFormatNullOptional() {
        Optional testOptional = null;

        AnnotatedObject annotatedObject = new AnnotatedObject(testOptional, mockLoggable);

        assertThat("", testee.format(annotatedObject), is("null"));
    }
}
