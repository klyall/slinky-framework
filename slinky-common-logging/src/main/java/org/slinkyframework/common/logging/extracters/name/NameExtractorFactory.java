package org.slinkyframework.common.logging.extracters.name;

import org.slinkyframework.common.aop.domain.AnnotatedObject;
import org.slinkyframework.common.logging.Loggable;

import java.lang.reflect.Field;
import java.util.Optional;

public final class NameExtractorFactory {

    private static FieldNameExtractor fieldNameExtractor = new FieldNameExtractor();
    private static LoggableNameExtractor loggableNameExtractor = new LoggableNameExtractor();
    private static ClassNameExtractor classNameExtractor = new ClassNameExtractor();

    public static Optional<String> extractName(AnnotatedObject annotatedObject, Field field) {
        if (field != null) {
            return fieldNameExtractor.extractName(annotatedObject, (Field) field);
        } else {
            return extractName(annotatedObject);
        }
    }

    public static Optional<String> extractName(AnnotatedObject annotatedObject) {
        if (annotatedObject.getAnnotation() instanceof Loggable) {
            return loggableNameExtractor.extractName(annotatedObject, null);
        } else {
            return classNameExtractor.extractName(annotatedObject, null);
        }
    }
}
