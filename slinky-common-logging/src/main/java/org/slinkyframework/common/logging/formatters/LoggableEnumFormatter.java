package org.slinkyframework.common.logging.formatters;

public class LoggableEnumFormatter extends LoggableTypeFormatter<Enum> {

    public String extractValue(Enum obj) {
        return obj.toString();
    }
}
