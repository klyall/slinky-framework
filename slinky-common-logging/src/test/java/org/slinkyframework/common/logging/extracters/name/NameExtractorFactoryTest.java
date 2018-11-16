package org.slinkyframework.common.logging.extracters.name;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slinkyframework.common.aop.domain.AnnotatedObject;
import org.slinkyframework.common.logging.Loggable;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//@RunWith(MockitoJUnitRunner.class)
@RunWith(Parameterized.class)
public class NameExtractorFactoryTest {

    private AnnotatedObject annotatedObject;
    private Field field;
    private Optional<String> expectedName;

    @Loggable("accountNumber")
    private static String fieldWithNamedLoggable;

    @Loggable
    private static String fieldWithAnonymousLoggable;

    public NameExtractorFactoryTest(String scenario, Optional<String> expectedName, AnnotatedObject annotatedObject, Field field) {
        this.expectedName = expectedName;
        this.annotatedObject = annotatedObject;
        this.field = field;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<Object[]> data() {

        Loggable mockAnonymousLoggable = mock(Loggable.class);
        when(mockAnonymousLoggable.value()).thenReturn("");

        Loggable mockNamedLoggable = mock(Loggable.class);
        when(mockNamedLoggable.value()).thenReturn("firstName");

        List<Object[]> scenarios = new ArrayList<>();
        scenarios.add(new Object[]{"Anonymous Loggable", Optional.empty(), new AnnotatedObject("Bob", mockAnonymousLoggable), null});
        scenarios.add(new Object[]{"Named Loggable", Optional.of("firstName"), new AnnotatedObject("Jane", mockNamedLoggable), null});

        Field namedLoggableField = (Field) ReflectionUtils.findField(NameExtractorFactoryTest.class, "fieldWithNamedLoggable");
        AnnotatedObject namedLoggableFieldAnnotatedObject =
                new AnnotatedObject(fieldWithNamedLoggable, namedLoggableField.getAnnotation(Loggable.class));

        scenarios.add(new Object[]{"Named Loggable Field", Optional.of("accountNumber"), namedLoggableFieldAnnotatedObject, namedLoggableField});

        Field anonymousLoggableField = (Field) ReflectionUtils.findField(NameExtractorFactoryTest.class, "fieldWithAnonymousLoggable");
        AnnotatedObject anonymousLoggableFieldAnnotatedObject =
                new AnnotatedObject(fieldWithAnonymousLoggable, anonymousLoggableField.getAnnotation(Loggable.class));

        scenarios.add(new Object[] {
                "Anonymous Loggable Field",
                Optional.of("fieldWithAnonymousLoggable"),
                anonymousLoggableFieldAnnotatedObject,
                anonymousLoggableField});

        return scenarios;
    }

    @Test
    public void shouldExtractName() {
        Optional<String> name = NameExtractorFactory.extractName(annotatedObject, field);

        assertThat(name, is(expectedName));
    }
}