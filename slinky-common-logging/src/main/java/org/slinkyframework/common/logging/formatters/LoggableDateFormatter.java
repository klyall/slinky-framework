package org.slinkyframework.common.logging.formatters;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

class LoggableDateFormatter extends LoggableTypeFormatter<Date> {

    String extractValue(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toString();
    }
}
