package org.slinkyframework.environment.config.maven.plugin.config.templates;

import org.codehaus.plexus.interpolation.ValueSource;
import org.slinkyframework.environment.config.maven.plugin.config.EnvironmentConfigException;

import java.util.List;

import static java.lang.String.format;

public class ManadatoryValueSource implements ValueSource {

    private ValueSource valueSource;

    public ManadatoryValueSource(ValueSource valueSource) {
        this.valueSource = valueSource;
    }

    @Override
    public Object getValue(String expression) {
        Object value = valueSource.getValue(expression);

        if (value == null) {
            throw new EnvironmentConfigException(format("The property '%s' is missing from the configuration files", expression));
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
