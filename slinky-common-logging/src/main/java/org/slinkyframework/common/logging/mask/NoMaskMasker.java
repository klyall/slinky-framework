package org.slinkyframework.common.logging.mask;

public class NoMaskMasker extends Mask {

    public static Mask getInstance() {
        return new NoMaskMasker();
    }

    @Override
    public String format(String source) {
        return source;
    }
}
