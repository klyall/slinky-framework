package org.slinkyframework.common.logging.extracters.name;

import org.slinkyframework.common.aop.domain.AnnotatedObject;
import org.slinkyframework.common.logging.Loggable;

import java.lang.reflect.Field;
import java.util.Optional;

class ClassNameExtractor implements NameExtractor {

    @Override
    public Optional<String> extractName(AnnotatedObject<?, Loggable> annotatedObject, Field field) {
        Object obj = annotatedObject.getObject();

        int lastDot = obj.getClass().getName().lastIndexOf(".");

        return Optional.ofNullable(obj.getClass().getName().substring(lastDot + 1));
    }
}
