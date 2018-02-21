package org.slinkyframework.application;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class SlinkyApplicationArchitecture {

    @Pointcut("within(org.slinkyframework.application.SlinkyApplication+)")
    public void inApplication() {}

    @Pointcut("cflowbelow(execution(* org.slinkyframework.application.SlinkyApplication+.*(..)))")
    public void inSubsequentCall() {}

    @Pointcut("execution(public * *(..))")
    public void publicOperations() {}

    @Pointcut("publicOperations() && inApplication() && !inSubsequentCall()")
    public void applicationOperations() {
    }
}
