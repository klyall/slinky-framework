package org.slinkyframework.common.logging.matchers;

import ch.qos.logback.classic.spi.LoggingEvent;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class HasLogMessageMatcher extends TypeSafeMatcher<LoggingEvent> {
    private Matcher<String> matcher;

    public HasLogMessageMatcher(Matcher matcher) {
        this.matcher = matcher;
    }

    @Override
    protected boolean matchesSafely(LoggingEvent loggingEvent) {
        return matcher.matches(loggingEvent.getFormattedMessage());
    }

    @Override
    public void describeTo(Description description) {
        description.appendDescriptionOf(matcher);
    }
}
