package org.slinkyframework.common.logging.formatters;

import org.slinkyframework.common.aop.domain.AnnotatedObject;
import org.slinkyframework.common.logging.Loggable;
import org.slinkyframework.common.logging.extracters.name.NameExtractorFactory;
import org.slinkyframework.common.logging.mask.Mask;

import java.lang.reflect.Field;
import java.util.Optional;

abstract class LoggableTypeFormatter<T> implements LoggableFormatter<T> {

    static final String NULL = "null";

    abstract String extractValue(T obj);

    @Override
    public String format(AnnotatedObject<T, Loggable> annotatedObject) {
        return format(annotatedObject, null);
    }

    @Override
    public String format(AnnotatedObject<T, Loggable> annotatedObject, Field field) {
        Optional<String> name = extractName(annotatedObject, field);
        String value = annotatedObject.getObject() == null ? NULL : extractMaskedValue(annotatedObject);

        if (name.isPresent()) {
            return name.get() + "=" + value;
        } else {
            return value;
        }
    }

    protected Optional<String> extractName(AnnotatedObject<T, Loggable> annotatedObject, Field field) {
        return NameExtractorFactory.extractName(annotatedObject, field);
    }

    private String extractMaskedValue(AnnotatedObject<T, Loggable> annotatedObject) {
        String rawValue = extractValue(annotatedObject.getObject());

        return getPrefix() + maskValue(rawValue, annotatedObject.getAnnotation()) + getSuffix();
    }

    private String maskValue(String rawValue, Loggable loggable) {

        if (loggable.mask() == null ) {
            return rawValue;
        }

        try {
            Mask mask = loggable.mask().newInstance();

            return mask.mask(rawValue);

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return "";
        }
    }

    String getPrefix() {
        return "";
    }

    String getSuffix() {
        return "";
    }
}
