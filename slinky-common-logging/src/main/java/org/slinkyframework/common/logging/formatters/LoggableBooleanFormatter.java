package org.slinkyframework.common.logging.formatters;

public class LoggableBooleanFormatter extends LoggableTypeFormatter<Boolean> {

    String extractValue(Boolean obj) {
        return obj.toString();
    }
}
