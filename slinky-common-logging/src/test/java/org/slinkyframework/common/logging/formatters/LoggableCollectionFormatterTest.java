package org.slinkyframework.common.logging.formatters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slinkyframework.common.aop.domain.AnnotatedObject;
import org.slinkyframework.common.logging.Loggable;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoggableCollectionFormatterTest {

    @Mock
    private Loggable mockLoggable;

    private LoggableFormatter testee = new LoggableCollectionFormatter();

    @Before
    public void setUp() {
        when(mockLoggable.value()).thenReturn("");
    }

    @Test
    public void shouldFormatCollectionWithoutAnnotationName() {
        List<String> testCollection = Arrays.asList("A", "B");

        AnnotatedObject annotatedObject = new AnnotatedObject(testCollection, mockLoggable);

        assertThat("Formatted string", testee.format(annotatedObject), is("(size=2)"));
    }

    @Test
    public void shouldFormatCollectionWithAnnotationName() {
        Collection testCollection = Arrays.asList("C", "D");

        when(mockLoggable.value()).thenReturn("types");

        AnnotatedObject annotatedObject = new AnnotatedObject(testCollection, mockLoggable);

        assertThat("Formatted string", testee.format(annotatedObject), is("types=(size=2)"));
    }

    @Test
    public void shouldFormatNullCollection() {
        Collection testCollection = null;

        AnnotatedObject annotatedObject = new AnnotatedObject(testCollection, mockLoggable);

        assertThat("", testee.format(annotatedObject), is("null"));
    }
}
