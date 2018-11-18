package org.slinkyframework.common.logging.formatters;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

class LoggableDateFormatter extends LoggableTypeFormatter<Date> {

    String extractValue(Date obj) {
        if (obj instanceof java.sql.Date) {
            return ((java.sql.Date) obj).toLocalDate().toString();
        } else if (obj instanceof java.sql.Time) {
            return ((java.sql.Time) obj).toLocalTime().toString();
        } else {
            return LocalDateTime.ofInstant(obj.toInstant(), ZoneId.systemDefault()).toString();
        }
    }
}
