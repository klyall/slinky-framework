package org.slinkyframework.common.logging.mask;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

public class BankAccountNumberMaskerTest {

    private Mask testee = new BankAccountNumberMasker();

    @Test
    public void shouldReturnNullForNullValue() {
        assertThat("", testee.mask(null), is(nullValue()));
    }

    @Test
    public void shouldReturnTheSourceValueMasked() {
        String testSource = "12345678901234";

        assertThat("Masked string length", testee.mask(testSource).length(), is(testSource.length()));
        assertThat("Masked string", testee.mask(testSource), is("**********1234"));
    }

    @Test
    public void shouldReturnErrorIfStringTooShortToBeAnAccountNumber() {
        String testSource = "123";

        assertThat("Masked string", testee.mask(testSource), startsWith("@Loggable error"));
    }
}
