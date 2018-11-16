package org.slinkyframework.common.logging.extracters.name;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slinkyframework.common.aop.domain.AnnotatedObject;
import org.slinkyframework.common.logging.Loggable;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoggableNameExtractorTest {

    @Mock
    private Loggable mockLoggable;

    private NameExtractor testee = new LoggableNameExtractor();

    @Test
    public void shouldReturnEmptyWhenNameNotProvidedInValue() {
        when(mockLoggable.value()).thenReturn("");
        AnnotatedObject annotatedObject = new AnnotatedObject("", mockLoggable);

        Optional<String> actualName = testee.extractName(annotatedObject, null);

        assertThat("Name", actualName.isPresent(), is(false));
    }

    @Test
    public void shouldReturnNameWhenProvidedInLoggableValue() {
        String expectedName = "customerId";
        AnnotatedObject annotatedObject = new AnnotatedObject("", mockLoggable);

        when(mockLoggable.value()).thenReturn(expectedName);

        Optional<String> actualName = testee.extractName(annotatedObject, null);

        assertThat("Optional", actualName.isPresent(), is(true));
        assertThat("Name", actualName.get(), is(expectedName));
    }
}