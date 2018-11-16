package org.slinkyframework.common.logging.extracters.name;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.slinkyframework.common.aop.domain.AnnotatedObject;
import org.slinkyframework.common.logging.Loggable;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class FieldNameExtractorTest {

    private NameExtractor testee = new FieldNameExtractor();

    @Loggable("firtsName")
    private String fieldWithNamedLoggable;

    @Loggable
    private String fieldWithAnonymousLoggable;

    @Test
    public void shouldReturnLoggableValueAsName() {
        Field field = (Field) ReflectionUtils.findField(FieldNameExtractorTest.class, "fieldWithNamedLoggable");
        AnnotatedObject annotatedObject = new AnnotatedObject(fieldWithNamedLoggable, field.getAnnotation(Loggable.class));

        Optional<String> actualName = testee.extractName(annotatedObject, field);

        assertThat("Optional", actualName.isPresent(), is(true));
        assertThat("Name", actualName.get(), is(("firtsName")));
    }

    @Test
    public void shouldReturnFieldNameWhenNoLoggableValue() {
        Field field = (Field) ReflectionUtils.findField(FieldNameExtractorTest.class, "fieldWithAnonymousLoggable");
        AnnotatedObject annotatedObject = new AnnotatedObject(fieldWithAnonymousLoggable, field.getAnnotation(Loggable.class));

        Optional<String> actualName = testee.extractName(annotatedObject, field);

        assertThat("Optional", actualName.isPresent(), is(true));
        assertThat("Name", actualName.get(), is(("fieldWithAnonymousLoggable")));
    }
}