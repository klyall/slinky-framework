package org.slinkyframework.environment.builder;

public class EnvironmentBuilderException extends RuntimeException {

    public EnvironmentBuilderException() {
    }

    public EnvironmentBuilderException(String message) {
        super(message);
    }

    public EnvironmentBuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnvironmentBuilderException(Throwable cause) {
        super(cause);
    }

    public EnvironmentBuilderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
