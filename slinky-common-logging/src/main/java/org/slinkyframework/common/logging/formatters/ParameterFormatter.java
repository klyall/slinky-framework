package org.slinkyframework.common.logging.formatters;

import org.slinkyframework.common.aop.domain.AnnotatedObject;

import java.util.List;
import java.util.stream.Collectors;

import static org.slinkyframework.common.logging.util.ListUtils.join;

public class ParameterFormatter {

    private LoggableFormatterFactory formatterFactory = new LoggableFormatterFactory();

    public String format(List<AnnotatedObject> annotatedObjects) {
        return wrapFormattedString(join(formatAnnotatedObjects(annotatedObjects)));
    }

    public String format(AnnotatedObject annotatedObject) {
        return wrapFormattedString(formatAnnotatedObject(annotatedObject));
    }

    private String wrapFormattedString(String formattedString) {
        StringBuilder parametersString = new StringBuilder();
        parametersString.append("[");
        parametersString.append(formattedString);
        parametersString.append("]");

        return parametersString.toString();
    }

    private List<String> formatAnnotatedObjects(List<AnnotatedObject> annotatedObjects) {
        return annotatedObjects.stream()
                .map(this::formatAnnotatedObject)
                .collect(Collectors.toList());
    }

    private String formatAnnotatedObject(AnnotatedObject annotatedObject) {
        return formatterFactory.getInstance(annotatedObject).format(annotatedObject, null);
    }
}

