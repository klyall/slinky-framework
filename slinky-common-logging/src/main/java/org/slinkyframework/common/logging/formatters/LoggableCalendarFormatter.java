package org.slinkyframework.common.logging.formatters;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class LoggableCalendarFormatter extends LoggableTypeFormatter<Calendar> {

    String extractValue(Calendar obj) {
        ZonedDateTime zonedDateTime;

        if (obj instanceof GregorianCalendar) {
            zonedDateTime = ((GregorianCalendar) obj).toZonedDateTime();
        } else {
            zonedDateTime = ZonedDateTime.ofInstant(obj.toInstant(), ZoneId.systemDefault());
        }

        return zonedDateTime.toString();
    }
}
