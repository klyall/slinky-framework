package org.slinkyframework.common.logging.test.example;

import org.slinkyframework.common.logging.Loggable;

public class ExampleName {

    @Loggable private String firstName;
    private String lastName;

    public ExampleName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
