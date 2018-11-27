package org.slinkyframework.common.logging.formatters;

class LoggableEnumFormatter extends LoggableTypeFormatter<Enum> {

    public String extractValue(Enum obj) {
        return obj.toString();
    }
}
