package org.slinkyframework.common.logging.formatters;

class LoggableBooleanFormatter extends LoggableTypeFormatter<Boolean> {

    String extractValue(Boolean obj) {
        return obj.toString();
    }
}
