package org.slinkyframework.common.logging.test.example;

import org.slinkyframework.common.logging.Loggable;

public class ExamplePerson {

    @Loggable private int id;
    @Loggable ExampleName name;

    public ExamplePerson(int id, ExampleName name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public ExampleName getName() {
        return name;
    }
}
