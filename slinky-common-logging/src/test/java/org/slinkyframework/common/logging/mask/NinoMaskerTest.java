package org.slinkyframework.common.logging.mask;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class NinoMaskerTest {

    private Mask testee = new NinoMasker();

    @Test
    public void shouldReturnNullForNullValue() {
        assertThat("", testee.mask(null), is(nullValue()));
    }

    @Test
    public void shouldReturnTheSourceValueMasked() {
        String testSource = "AB123456D";

        assertThat("Masked string length", testee.mask(testSource).length(), is(testSource.length()));
        assertThat("Masked string", testee.mask(testSource), is("AB******D"));
    }

    @Test
    public void shouldReturnTheSourceValueUnmaskedIfStringTooShortToBeAnAccountNumber() {
        String testSource = "AB 12 34 56 D";

        assertThat("Masked string length", testee.mask(testSource).length(), is(testSource.length()));
        assertThat("Masked string", testee.mask(testSource), is("AB ** ** ** D"));
    }
}
