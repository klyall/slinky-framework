package org.slinkyframework.common.logging.mask;

public class BankAccountNumberMasker extends Mask {

    @Override
    String format(String source) {
        int length = source.length();
        if (length > 4) {
            return ASTERISKS.substring(0, length - 4) + source.substring(length - 4);
        } else {
            return "@Loggable error: Too short to be a bank account number";
        }
    }
}
