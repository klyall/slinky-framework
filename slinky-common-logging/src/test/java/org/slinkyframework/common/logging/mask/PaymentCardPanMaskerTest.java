package org.slinkyframework.common.logging.mask;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

public class PaymentCardPanMaskerTest {

    private Mask testee = new PaymentCardPanMasker();

    @Test
    public void shouldReturnNullForNullValue() {
        assertThat("", testee.mask(null), is(nullValue()));
    }

    @Test
    public void shouldReturnTheSourceValueMasked() {
        String testSource = "1234567890123456";

        assertThat("Masked string length", testee.mask(testSource).length(), is(testSource.length()));
        assertThat("Masked string", testee.mask(testSource), is("123456******3456"));
    }

    @Test
    public void shouldReturnErrorIfStringTooShortToBeAnPaymentCardPan() {
        String testSource = "123";

        assertThat("Masked string", testee.mask(testSource), startsWith("@Loggable error"));
    }
}
