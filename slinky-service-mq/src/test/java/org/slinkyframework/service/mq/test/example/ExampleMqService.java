package org.slinkyframework.service.mq.test.example;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

public class ExampleMqService {

    @Transactional(propagation = REQUIRES_NEW)
    @JmsListener(destination = "exampleQueue")
    public void processMessage(ExampleRequest request) {
    }

    @Transactional(propagation = REQUIRES_NEW)
    @JmsListener(destination = "exampleException")
    public void processException(ExampleRequest request) {
        throw new IllegalArgumentException("Forced exception");
    }
}
