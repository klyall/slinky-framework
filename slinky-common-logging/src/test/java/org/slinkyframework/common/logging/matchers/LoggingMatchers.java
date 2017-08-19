package org.slinkyframework.common.logging.matchers;

import org.hamcrest.Matcher;

import java.util.regex.Pattern;

public class LoggingMatchers {

    public static HasLogMessageMatcher hasLogMessage(Matcher<String> matcher) {
        return new HasLogMessageMatcher(matcher);
    }

    /**
     * Creates a matcher of {@link java.lang.String} that matches when the examined string
     * exactly matches the given {@link java.util.regex.Pattern}.
     */
    public static Matcher<String> matchesPattern(Pattern pattern) {
        return new MatchesPatternMatcher(pattern);
    }

    /**
     * Creates a matcher of {@link java.lang.String} that matches when the examined string
     * exactly matches the given regular expression, treated as a {@link java.util.regex.Pattern}.
     */
    public static Matcher<String> matchesPattern(String regex) {
        return new MatchesPatternMatcher(Pattern.compile(regex));
    }
}
