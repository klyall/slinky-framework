package org.slinkyframework.common.logging.mask;

public class NinoMasker extends Mask {

    private static final String DIGITS = "\\d";

    @Override
    String format(String source) {
        return source.replaceAll(DIGITS, "*");
    }
}
