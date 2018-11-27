package org.slinkyframework.common.logging.mask;

public abstract class Mask {
    String ASTERISKS = "**************************************************";

    abstract String format(String source);

    public String mask(String source) {
        if (source == null) {
            return source;
        } else {
            return format(source);
        }
    }
}
