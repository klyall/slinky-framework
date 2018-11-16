package org.slinkyframework.common.logging.formatters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slinkyframework.common.aop.domain.AnnotatedObject;
import org.slinkyframework.common.logging.Loggable;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoggableCharacterFormatterTest {

    @Mock
    private Loggable mockLoggable;

    private LoggableFormatter testee = new LoggableCharacterFormatter();

    @Before
    public void setUp() {
        when(mockLoggable.value()).thenReturn("");
    }

    @Test
    public void shouldFormatCharacterWithoutAnnotationName() {
        char testCharacter = 'a';

        AnnotatedObject annotatedObject = new AnnotatedObject(testCharacter, mockLoggable);

        assertThat("Formatted string", testee.format(annotatedObject), is("'a'"));
    }

    @Test
    public void shouldFormatCharacterWithAnnotationName() {
        char testCharacter = 'b';

        when(mockLoggable.value()).thenReturn("type");

        AnnotatedObject annotatedObject = new AnnotatedObject(testCharacter, mockLoggable);

        assertThat("Formatted string", testee.format(annotatedObject), is("type='b'"));
    }

    @Test
    public void shouldFormatNullCharacter() {
        Character testCharacter = null;

        AnnotatedObject annotatedObject = new AnnotatedObject(testCharacter, mockLoggable);

        assertThat("", testee.format(annotatedObject), is("null"));
    }
}
