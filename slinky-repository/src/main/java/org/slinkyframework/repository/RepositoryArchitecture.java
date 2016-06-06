package org.slinkyframework.repository;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class RepositoryArchitecture {

    @Pointcut("within(org.slinkyframework.repository.Repository+)")
    public void inRepository() {}

    @Pointcut("execution(public * *(..))")
    public void publicOperations() {}

    @Pointcut("publicOperations() && inRepository()")
    public void repositoryOperations() {
    }
}
