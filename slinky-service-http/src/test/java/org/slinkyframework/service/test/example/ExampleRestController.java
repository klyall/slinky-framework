package org.slinkyframework.service.test.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleRestController {

    @RequestMapping("/")
    public String publicEndpoint() {
        return publicMethod();
    }

    @RequestMapping("/exception")
    public String exceptionMethod() {
        throw new IllegalArgumentException("Forced exception to test logging and exception handling");
    }

    public String publicMethod() {
        return privateMethod();
    }

    private String privateMethod() {
        return new ExternalClass().publicMethod();
    }
}
