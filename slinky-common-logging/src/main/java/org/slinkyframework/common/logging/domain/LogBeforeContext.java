package org.slinkyframework.common.logging.domain;

public class LogBeforeContext {
    private final String className;
    private final String methodName;
    private final String loggableParameters;

    public LogBeforeContext(String className, String methodName, String loggableParameters) {
        this.className = className;
        this.methodName = methodName;
        this.loggableParameters = loggableParameters;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getLoggableParameters() {
        return loggableParameters;
    }
}
