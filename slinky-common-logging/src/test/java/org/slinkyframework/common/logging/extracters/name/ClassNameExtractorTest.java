package org.slinkyframework.common.logging.extracters.name;

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

@RunWith(MockitoJUnitRunner.class)
public class ClassNameExtractorTest {

    @Mock
    private Loggable mockLoggable;
    private ClassNameExtractor testee = new ClassNameExtractor();

    @Test
    public void shouldReturnClassNameForClassWithPackage() {
        ExampleName exampleName = new ExampleName("Joe", "Bloggs");
        AnnotatedObject annotatedObject = new AnnotatedObject(exampleName, mockLoggable);

        assertThat(testee.extractName(annotatedObject, null).get(), is("ExampleName"));
    }

    @Test
    public void shouldReturnClassNameForOptionalClass() {
        ExampleName exampleName = new ExampleName("Joe", "Bloggs");
        AnnotatedObject annotatedObject = new AnnotatedObject(Optional.of(exampleName), mockLoggable);

        assertThat(testee.extractName(annotatedObject, null).get(), is("Optional"));
    }

}
