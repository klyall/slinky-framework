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

    @Loggable("firstName")
    private String fieldWithValueAttributeNamedLoggable;

    @Loggable(name = "secondName")
    private String fieldWithNameAttributeNamedLoggable;

    @Loggable(name = "firstName", value = "lastName")
    private String fieldWithNameAndValueAttribute;

    @Loggable
    private String fieldWithAnonymousLoggable;

    @Test
    public void shouldReturnLoggableValueAttributeAsName() {
        Field field = (Field) ReflectionUtils.findField(FieldNameExtractorTest.class, "fieldWithValueAttributeNamedLoggable");
        AnnotatedObject annotatedObject = new AnnotatedObject(fieldWithValueAttributeNamedLoggable, field.getAnnotation(Loggable.class));

        Optional<String> actualName = testee.extractName(annotatedObject, field);

        assertThat("Optional", actualName.isPresent(), is(true));
        assertThat("Name", actualName.get(), is(("firstName")));
    }

    @Test
    public void shouldReturnLoggableNameAttributeAsName() {
        Field field = (Field) ReflectionUtils.findField(FieldNameExtractorTest.class, "fieldWithNameAttributeNamedLoggable");
        AnnotatedObject annotatedObject = new AnnotatedObject(fieldWithNameAttributeNamedLoggable, field.getAnnotation(Loggable.class));

        Optional<String> actualName = testee.extractName(annotatedObject, field);

        assertThat("Optional", actualName.isPresent(), is(true));
        assertThat("Name", actualName.get(), is(("secondName")));
    }

    @Test
    public void shouldReturnNameAttributeWhenLoggableNameAndValueAttributeBothSpecified() {
        Field field = (Field) ReflectionUtils.findField(FieldNameExtractorTest.class, "fieldWithNameAndValueAttribute");
        AnnotatedObject annotatedObject = new AnnotatedObject(fieldWithNameAndValueAttribute, field.getAnnotation(Loggable.class));

        Optional<String> actualName = testee.extractName(annotatedObject, field);

        assertThat("Optional", actualName.isPresent(), is(true));
        assertThat("Name", actualName.get(), is(("firstName")));
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