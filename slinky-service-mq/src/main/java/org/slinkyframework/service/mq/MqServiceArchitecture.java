package org.slinkyframework.service.mq;

import org.aspectj.lang.annotation.Pointcut;

public class MqServiceArchitecture {

    @Pointcut("execution(@org.springframework.jms.annotation.JmsListener * *(..))")
    public void inJmsListener() {}

    @Pointcut("inJmsListener()")
    public void serviceEndpoint() {}
}
