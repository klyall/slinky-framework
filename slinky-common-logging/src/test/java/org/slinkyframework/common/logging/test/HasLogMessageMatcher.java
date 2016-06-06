package org.slinkyframework.common.logging.test;

import ch.qos.logback.classic.spi.LoggingEvent;
import org.hamcrest.Description;
import org.mockito.ArgumentMatcher;

public class HasLogMessageMatcher extends ArgumentMatcher {
    private String expectedMessage;

    public HasLogMessageMatcher(String expectedMessage) {
        this.expectedMessage = expectedMessage;
    }

    @Override
    public boolean matches(Object argument) {

        return ((LoggingEvent) argument).getFormattedMessage().matches(expectedMessage);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(expectedMessage);
    }
}
