package org.slinkyframework.common.logging.extracters.name;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slinkyframework.common.aop.domain.AnnotatedObject;
import org.slinkyframework.common.logging.Loggable;

import java.lang.reflect.Field;
import java.util.Optional;

final class FieldNameExtractor implements NameExtractor {

    private static final Logger LOGGER = LoggerFactory.getLogger(FieldNameExtractor.class);

    @Override
    public Optional<String> extractName(AnnotatedObject<?, Loggable> annotatedObject, Field field) {

        Optional<String> name = extractName(annotatedObject);

        if (name.isPresent()) {
            return name;
        } else {
            return Optional.of(field.getName());
        }
    }

    private Optional<String> extractName(AnnotatedObject<?, Loggable> annotatedObject) {
        return NameExtractorFactory.extractName(annotatedObject, null);
    }
}
