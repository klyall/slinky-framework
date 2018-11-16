package org.slinkyframework.common.logging.formatters;

public class LoggableStringFormatter extends LoggableTypeFormatter<String> {

    String extractValue(String obj) {
        return "'" + obj + "'";
    }
}
