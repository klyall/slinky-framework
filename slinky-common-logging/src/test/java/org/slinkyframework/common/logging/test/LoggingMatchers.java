package org.slinkyframework.common.logging.test;

public class LoggingMatchers {

    public static HasLogMessageMatcher hasLogMessage(String expectedResponseMessage) {
        return new HasLogMessageMatcher(expectedResponseMessage);
    }
}
