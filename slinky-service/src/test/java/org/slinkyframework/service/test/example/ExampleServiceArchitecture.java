package org.slinkyframework.service.test.example;

import org.aspectj.lang.annotation.Pointcut;

public class ExampleServiceArchitecture {
    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void inService() {}

    @Pointcut("execution(public * *(..))")
    public void publicOperations() {}

    @Pointcut("cflowbelow(publicOperations())")
    public void inSubsequentCall() {}

    @Pointcut("inService() && publicOperations()")
    public void serviceEndpoints() {}
}
