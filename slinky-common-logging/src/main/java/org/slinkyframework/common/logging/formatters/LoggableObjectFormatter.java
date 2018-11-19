package org.slinkyframework.common.logging.formatters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slinkyframework.common.aop.domain.AnnotatedObject;
import org.slinkyframework.common.logging.Loggable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.slinkyframework.common.logging.util.ListUtils.join;

public class LoggableObjectFormatter extends LoggableTypeFormatter<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggableObjectFormatter.class);
    public static final String NOT_VISIBLE = "NOT_VISIBLE";

    private LoggableFormatterFactory formatterFactory;

    private Predicate<? super Field> isLoggableField = field -> field.getAnnotation(Loggable.class) != null;

    public LoggableObjectFormatter(LoggableFormatterFactory formatterFactory) {
        this.formatterFactory = formatterFactory;
    }

    @Override
    String extractValue(Object obj) {
        return new StringBuilder()
                .append("(")
                .append(join(formatLoggableFieldsFromObject(obj)))
                .append(")")
                .toString();
    }

    private List<String> formatLoggableFieldsFromObject(Object obj) {

        return getFields(obj.getClass()).stream()
                .filter(isLoggableField)
                .map(f -> formatAnnotatedObject(obj, f))
                .collect(Collectors.toList());
    }

    private List<Field> getFields(Class clazz) {

        if (clazz.getSuperclass() == null) {
            return new ArrayList<>();
        } else {
            List<Field> fields = getFields(clazz.getSuperclass());
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            return fields;
        }
    }

    private String formatAnnotatedObject(Object obj, Field field) {
        AnnotatedObject annotatedObject = toAnnotatedObject(obj, field);

        return formatterFactory.getInstance(annotatedObject).format(annotatedObject, field);
    }

    private AnnotatedObject toAnnotatedObject(Object obj, Field field) {
        Loggable loggable = field.getAnnotation(Loggable.class);
        Object value = extractFieldValue(obj, field);

        return new AnnotatedObject(value, loggable);
    }

    private static Object extractFieldValue(Object obj, Field field) {
        Method[] methods = obj.getClass().getMethods();
        String fieldName = field.getName();
        String getterMethod = "get" + fieldName;
        String isMethod = "is" + fieldName;

        for (Method method: methods) {
            if (method.getName().equalsIgnoreCase(getterMethod)
                    || method.getName().equalsIgnoreCase(isMethod)) {
                try {
                    return method.invoke(obj, (Object[]) null);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    LOGGER.warn("Unable to extract Loggable field '{}': {}", fieldName, e.getMessage());
                }
            }
        }
        return NOT_VISIBLE;
    }
}
