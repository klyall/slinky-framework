package org.slinkyframework.common.logging.formatters;

import org.slinkyframework.common.aop.domain.AnnotatedObject;
import org.slinkyframework.common.logging.Loggable;

import java.lang.reflect.Field;

public interface LoggableFormatter<T> {
    String format(AnnotatedObject<T, Loggable> annotatedObject);
    String format(AnnotatedObject<T, Loggable> annotatedObject, Field field);
}
