package org.slinkyframework.environment.config.maven.plugin.config.templates;

import org.codehaus.plexus.interpolation.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LoggingValueSource implements ValueSource {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingValueSource.class);

    private ValueSource valueSource;

    public LoggingValueSource(ValueSource valueSource) {
        this.valueSource = valueSource;
    }

    @Override
    public Object getValue(String expression) {
        Object value = valueSource.getValue(expression);

        if (value == null) {
            LOG.debug("Warning: Property not found '{}'", expression);
        }

        return value;
    }

    @Override
    public List getFeedback() {
        return valueSource.getFeedback();
    }

    @Override
    public void clearFeedback() {
        valueSource.clearFeedback();
    }
}
