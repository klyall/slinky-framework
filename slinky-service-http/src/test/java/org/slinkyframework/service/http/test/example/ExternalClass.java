package org.slinkyframework.service.http.test.example;

public class ExternalClass {

    public String publicMethod() {
        return privateMethod();
    }

    private String privateMethod() {
        return "Hello world!";
    }
}
