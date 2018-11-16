package org.slinkyframework.common.logging.formatters;

import org.slinkyframework.common.aop.domain.AnnotatedObject;

import java.net.URL;
import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

public class LoggableFormatterFactory {

    private LoggableBooleanFormatter booleanFormatter = new LoggableBooleanFormatter();
    private LoggableCharacterFormatter characterFormatter = new LoggableCharacterFormatter();
    private LoggableCollectionFormatter collectionFormatter = new LoggableCollectionFormatter();
    private LoggableCalendarFormatter calendarFormatter = new LoggableCalendarFormatter();
    private LoggableDateFormatter dateFormatter = new LoggableDateFormatter();
    private LoggableEnumFormatter enumFormatter = new LoggableEnumFormatter();
    private LoggableNumberFormatter numberFormatter = new LoggableNumberFormatter();
    private LoggableObjectFormatter objectFormatter = new LoggableObjectFormatter(this);
    private LoggableOptionalFormatter optionalFormatter = new LoggableOptionalFormatter(this);
    private LoggableStringFormatter stringFormatter = new LoggableStringFormatter();
    private LoggableTemporalFormatter temporalFormatter = new LoggableTemporalFormatter();
    private LoggableUrlFormatter urlFormatter = new LoggableUrlFormatter();

    public LoggableFormatter getInstance(AnnotatedObject annotatedObject) {

        if (annotatedObject.getObject() instanceof Boolean) {
            return booleanFormatter;
        } else if (annotatedObject.getObject() instanceof Calendar) {
            return calendarFormatter;
        } else if (annotatedObject.getObject() instanceof Character) {
            return characterFormatter;
        } else if (annotatedObject.getObject() instanceof Collection) {
            return collectionFormatter;
        } else if (annotatedObject.getObject() instanceof Date) {
            return dateFormatter;
        } else if (annotatedObject.getObject() instanceof Enum) {
            return enumFormatter;
        } else if (annotatedObject.getObject() instanceof Number) {
            return numberFormatter;
        } else if (annotatedObject.getObject() instanceof Optional) {
            return optionalFormatter;
        } else if (annotatedObject.getObject() instanceof String) {
            return stringFormatter;
        } else if (annotatedObject.getObject() instanceof Temporal) {
            return temporalFormatter;
        } else if (annotatedObject.getObject() instanceof URL) {
            return urlFormatter;
        } else {
            return objectFormatter;
        }
    }

}
