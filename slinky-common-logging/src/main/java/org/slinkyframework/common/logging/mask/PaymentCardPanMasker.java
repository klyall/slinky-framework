package org.slinkyframework.common.logging.mask;

public class PaymentCardPanMasker extends Mask {

    @Override
    String format(String source) {
        int length = source.length();
        if (length >= 13 && length <= 19) {
            return source.substring(0, 6) + ASTERISKS.substring(0, 6) + source.substring(length - 4);
        } else {
            return "@Loggable error: Does not look like a payment card number";
        }
    }
}
