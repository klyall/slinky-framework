package org.slinkyframework.common.logging.extracters.name;

import org.slinkyframework.common.aop.domain.AnnotatedObject;
import org.slinkyframework.common.logging.Loggable;

import java.lang.reflect.Field;
import java.util.Optional;

public interface NameExtractor {

    Optional<String> extractName(AnnotatedObject<?, Loggable> annotatedObject, Field field);
}
