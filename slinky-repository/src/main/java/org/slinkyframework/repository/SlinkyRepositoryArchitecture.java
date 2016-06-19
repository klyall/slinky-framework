package org.slinkyframework.repository;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class SlinkyRepositoryArchitecture {

    @Pointcut("within(org.slinkyframework.repository.SlinkyRepository+)")
    public void inRepository() {}

    @Pointcut("execution(public * *(..))")
    public void publicOperations() {}

    @Pointcut("publicOperations() && inRepository()")
    public void repositoryOperations() {
    }
}
