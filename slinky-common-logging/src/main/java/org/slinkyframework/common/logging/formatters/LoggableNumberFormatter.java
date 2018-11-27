package org.slinkyframework.common.logging.formatters;

class LoggableNumberFormatter extends LoggableTypeFormatter<Number> {

    public String extractValue(Number number) {
        return number.toString();
    }
}
