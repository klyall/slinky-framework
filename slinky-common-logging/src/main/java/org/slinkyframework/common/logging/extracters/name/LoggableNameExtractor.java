package org.slinkyframework.common.logging.extracters.name;

import org.slinkyframework.common.aop.domain.AnnotatedObject;
import org.slinkyframework.common.logging.Loggable;

import java.lang.reflect.Field;
import java.util.Optional;

final class LoggableNameExtractor implements NameExtractor {

    @Override
    public Optional<String> extractName(AnnotatedObject<?, Loggable> annotatedObject, Field field) {
        Loggable annotation = annotatedObject.getAnnotation();

        String value = null;

        if (nameAttributeProvided(annotation)) {
            value = annotation.name().trim();
        } else if (valueAttributeProvided(annotation)) {
            value = annotation.value().trim();
        }

        return Optional.ofNullable(value);
    }

    private boolean valueAttributeProvided(Loggable annotation) {
        return !annotation.value().trim().equals("");
    }

    private boolean nameAttributeProvided(Loggable annotation) {
        return annotation.name() != null && !annotation.name().trim().equals("");
    }
}
