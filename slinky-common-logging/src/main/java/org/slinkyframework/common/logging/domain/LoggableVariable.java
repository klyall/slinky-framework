package org.slinkyframework.common.logging.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import static java.lang.String.format;

public class LoggableVariable {

    private Optional<String> name;
    private Object value;

    public LoggableVariable(Optional<String> name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (name.isPresent()) {
            sb.append(name.get());
            sb.append("=");
        }
        sb.append(convertToString(value));

        return sb.toString();
    }

    private static String convertToString(Object obj) {
        if (isQuotableString(obj)) {
            return toQuotedString(obj);
        } else if (isDate(obj)) {
            return toFormattedDateTimeString((Date) obj);
        } else if (isLoggableAsString(obj)) {
            return obj.toString();
        } else if (obj instanceof Collection<?>) {
            return format("(size=%d)", ((Collection) obj).size());
        }
        return "null";
    }

    private static boolean isLoggableAsString(Object obj) {
        return obj instanceof Integer
                || obj instanceof Short
                || obj instanceof Long
                || obj instanceof Float
                || obj instanceof Double
                || obj instanceof Byte
                || obj instanceof Character
                || obj instanceof Boolean
                || obj instanceof LocalDate
                || obj instanceof LocalDateTime
                || obj instanceof LocalTime
                || obj instanceof Enum
                || obj instanceof LoggableObject;
    }

    private static boolean isDate(Object obj) {
        return obj instanceof Date;
    }

    private static boolean isQuotableString(Object obj) {
        return obj instanceof String
                || obj instanceof Character;
    }

    private static String toFormattedDateTimeString(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toString();
    }

    private static String toQuotedString(Object obj) {
        return format("'%s'", obj.toString());
    }
}
