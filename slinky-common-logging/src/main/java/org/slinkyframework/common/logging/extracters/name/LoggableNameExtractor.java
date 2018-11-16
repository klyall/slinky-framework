package org.slinkyframework.common.logging.extracters.name;

import org.slinkyframework.common.aop.domain.AnnotatedObject;
import org.slinkyframework.common.logging.Loggable;

import java.lang.reflect.Field;
import java.util.Optional;

final class LoggableNameExtractor implements NameExtractor {

    @Override
    public Optional<String> extractName(AnnotatedObject<?, Loggable> annotatedObject, Field field) {
        Loggable annotation = annotatedObject.getAnnotation();

        return parameterNameProvided(annotation) ? Optional.of(annotation.value()) : Optional.empty();
    }

    private boolean parameterNameProvided(Loggable annotation) {
        return !annotation.value().trim().equals("");
    }
}
