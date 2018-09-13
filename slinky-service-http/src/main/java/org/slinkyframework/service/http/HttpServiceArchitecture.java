package org.slinkyframework.service.http;

import org.aspectj.lang.annotation.Pointcut;

public class HttpServiceArchitecture {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void inRestController() {}

    @Pointcut("execution(@org.springframework.web.bind.annotation.RequestMapping * *(..))")
    public void inEndpoint() {}

    @Pointcut("inRestController() && inEndpoint()")
    public void serviceEndpoint() {}
}
