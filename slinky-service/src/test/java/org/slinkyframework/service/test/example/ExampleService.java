package org.slinkyframework.service.test.example;

import org.springframework.stereotype.Service;

@Service
public class ExampleService {

    public void publicEndpoint() { }

    public void exceptionMethod() {
        throw new IllegalArgumentException("Forced exception");
    }

    public void firstEndpoint() {
        secondEndpoint();
    }

    public void secondEndpoint() {

    }

}
