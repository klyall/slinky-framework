package org.slinkyframework.common.logging.test.example;

import org.slinkyframework.common.logging.Loggable;

public class ClassWithoutAccessor {

    @Loggable private String string1;
    @Loggable private String string2;
    @Loggable private String string3;

    public ClassWithoutAccessor(String string1, String string2, String string3) {
        this.string1 = string1;
        this.string2 = string2;
        this.string3 = string3;
    }

    public String getString1() {
        return string1;
    }

    private String getString2() {
        return string2;
    }
}
