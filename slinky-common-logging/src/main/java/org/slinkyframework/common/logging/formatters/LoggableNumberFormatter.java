package org.slinkyframework.common.logging.formatters;

public class LoggableNumberFormatter extends LoggableTypeFormatter<Number> {

    public String extractValue(Number number) {
        return number.toString();
    }
}
