package org.slinkyframework.common.logging.domain;

public class LogExceptionContext {
    private final String className;
    private final String methodName;
    private final long durationInMs;
    private final Throwable exception;

    public LogExceptionContext(String className, String methodName, long durationInMs, Throwable exception) {
        this.className = className;
        this.methodName = methodName;
        this.durationInMs = durationInMs;
        this.exception = exception;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public long getDurationInMs() {
        return durationInMs;
    }

    public Throwable getException() {
        return exception;
    }
}
