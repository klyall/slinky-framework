package org.slinkyframework.common.logging.formatters;

import org.slinkyframework.common.aop.domain.AnnotatedObject;
import org.slinkyframework.common.logging.Loggable;
import org.slinkyframework.common.logging.mask.Mask;
import org.slinkyframework.common.logging.mask.NoMaskMasker;

import java.lang.annotation.Annotation;
import java.util.Optional;

class LoggableOptionalFormatter extends LoggableTypeFormatter<Optional> {

    private static final String EMPTY = "EMPTY";

    private LoggableFormatterFactory formatterFactory;

    public LoggableOptionalFormatter(LoggableFormatterFactory formatterFactory) {
        this.formatterFactory = formatterFactory;
    }

    String extractValue(Optional obj) {
        StringBuilder value = new StringBuilder()
                .append("Optional(")
                .append(formatValue(obj))
                .append(")");

        return value.toString();
    }

    private String formatValue(Optional obj) {
        if (obj.isPresent()) {
            return formatObject(obj.get());
        } else {
            return EMPTY;
        }
    }

    private String formatObject(Object obj) {
        Loggable loggable = createEmptyLoggable();
        AnnotatedObject annotatedObject = new AnnotatedObject(obj, loggable);

        LoggableFormatter formatter = formatterFactory.getInstance(annotatedObject);

        StringBuilder formattedString = new StringBuilder();

        if (formatter instanceof LoggableObjectFormatter) {
            formattedString.append(obj.getClass().getSimpleName());
        }

        formattedString.append(formatter.format(annotatedObject));

        return formattedString.toString();
    }

    private Loggable createEmptyLoggable() {
        return new Loggable() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return Loggable.class;
            }

            @Override
            public String value() {
                return "";
            }

            @Override
            public String name() {
                return "";
            }

            @Override
            public String fields() {
                return "";
            }

            @Override
            public Class<? extends Mask> mask() {
                return NoMaskMasker.class;
            }
        };
    }
}
