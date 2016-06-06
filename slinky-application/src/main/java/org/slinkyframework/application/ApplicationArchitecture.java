package org.slinkyframework.application;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ApplicationArchitecture {

    @Pointcut("within(org.slinkyframework.application.Application+)")
    public void inApplication() {}

    @Pointcut("execution(public * *(..))")
    public void publicOperations() {}

    @Pointcut("publicOperations() && inApplication()")
    public void applicationOperations() {
    }
}
