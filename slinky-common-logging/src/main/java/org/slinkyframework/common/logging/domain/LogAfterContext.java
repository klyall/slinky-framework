package org.slinkyframework.common.logging.domain;

public class LogAfterContext {
    private final String className;
    private final String methodName;
    private final long durationInMs;
    private final String loggableReturn;

    public LogAfterContext(String className, String methodName, long durationInMs, String loggableReturn) {
        this.className = className;
        this.methodName = methodName;
        this.durationInMs = durationInMs;
        this.loggableReturn = loggableReturn;
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

    public String getLoggableReturn() {
        return loggableReturn;
    }
}
