package org.slinkyframework.common.logging.formatters;

class LoggableStringFormatter extends LoggableTypeFormatter<String> {

    private static final String QUOTE = "'";

    String extractValue(String obj) {
        return obj;
    }

    @Override
    String getPrefix() {
        return QUOTE;
    }

    @Override
    String getSuffix() {
        return QUOTE;
    }
}
