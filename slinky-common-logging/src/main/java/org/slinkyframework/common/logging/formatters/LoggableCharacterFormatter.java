package org.slinkyframework.common.logging.formatters;

public class LoggableCharacterFormatter extends LoggableTypeFormatter<Character> {

    String extractValue(Character obj) {
        return "'" + String.valueOf(obj) + "'";
    }
}
