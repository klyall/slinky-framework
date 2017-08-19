package org.slinkyframework.common.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slinkyframework.common.aop.domain.AnnotatedObject;
import org.slinkyframework.common.logging.domain.LoggableObject;
import org.slinkyframework.common.logging.domain.LoggableVariable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static java.lang.String.format;
import static org.slinkyframework.common.logging.util.ListUtils.join;

public class ParameterExtracter {

    private static final Logger LOG = LoggerFactory.getLogger(ParameterExtracter.class);

    public static String convertLoggableParametersToString(List<AnnotatedObject> args) {
        StringBuilder parametersString = new StringBuilder();
        parametersString.append("[");
        parametersString.append(join(extractLoggableParamsFromMethod(args)));
        parametersString.append("]");

        return parametersString.toString();
    }

    public static String convertLoggableReturnToString(AnnotatedObject annotatedObject) {
        StringBuilder parametersString = new StringBuilder();
        parametersString.append("[");
        parametersString.append(extractLoggableVariable(annotatedObject));
        parametersString.append("]");

        return parametersString.toString();
    }

    private static List<LoggableVariable> extractLoggableParamsFromMethod(List<AnnotatedObject> args) {
        List<LoggableVariable> loggableVariables = new LinkedList<>();

        for (AnnotatedObject annotatedObject: args) {

            LoggableVariable loggableVariable = extractLoggableVariable(annotatedObject);
            loggableVariables.add(loggableVariable);
        }
        return loggableVariables;
    }

    private static LoggableVariable extractLoggableVariable(AnnotatedObject annotatedObject) {
        Loggable annotation = (Loggable) annotatedObject.getAnnotation();

        String fieldName = parameterNameProvided(annotation) ? annotation.value() : null;
        Object fieldValue = annotatedObject.getObject();

        if (fieldValue != null && !isCoreClass(fieldValue)) {
            fieldValue = extractLoggableObject(fieldValue);
        }

        return new LoggableVariable(Optional.ofNullable(fieldName), fieldValue);
    }

    private static LoggableObject extractLoggableObject(Object obj) {
        String className = extractClassName(obj);
        List<LoggableVariable> params = extractLoggableVariablesFromObject(obj);

        return new LoggableObject(className, params);
    }

    private static List<LoggableVariable> extractLoggableVariablesFromObject(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();

        List<LoggableVariable> loggableVariables = new LinkedList<>();

        for (Field field : fields) {
            if (isLoggable(field)) {
                Loggable loggable = field.getAnnotation(Loggable.class);

                String fieldName = loggable.value().equals("") ? field.getName() : loggable.value();
                Object fieldValue = extractFieldValue(obj, fieldName);

                if (fieldValue != null && !isCoreClass(fieldValue)) {
                    fieldValue = extractLoggableObject(fieldValue);
                }
                loggableVariables.add(new LoggableVariable(Optional.ofNullable(fieldName), fieldValue));
            }
        }
        return loggableVariables;
    }

    private static Object extractFieldValue(Object obj, String fieldName) {
        Method[] methods = obj.getClass().getMethods();

        for (Method method: methods) {
            if (method.getName().equalsIgnoreCase(format("get%s", fieldName))
                    || method.getName().equalsIgnoreCase(format("is%s", fieldName))) {
                try {
                    return method.invoke(obj, (Object[]) null);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    LOG.warn("Unable to extract Loggable field '{}': {}", fieldName, e.getMessage());
                }
            }
        }
        return null;
    }

    private static boolean isCoreClass(Object obj) {
        return obj instanceof String
                || obj instanceof Integer
                || obj instanceof Short
                || obj instanceof Long
                || obj instanceof Float
                || obj instanceof Date
                || obj instanceof Double
                || obj instanceof Byte
                || obj instanceof Character
                || obj instanceof Boolean
                || obj instanceof LocalDate
                || obj instanceof LocalDateTime
                || obj instanceof LocalTime
                || obj instanceof Enum
                || obj instanceof Collection<?>;
    }

    private static boolean isLoggable(Field field) {
        return field.getAnnotation(Loggable.class) != null;
    }

    private static String extractClassName(Object obj) {
        int lastDot = obj.getClass().getName().lastIndexOf(".");
        return obj.getClass().getName().substring(lastDot + 1);
    }

    private static boolean parameterNameProvided(Loggable annotation) {
        return !annotation.value().equals("");
    }
}
