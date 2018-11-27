package org.slinkyframework.common.logging.mask;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class NoMaskMaskerTest {

    private Mask testee = new NoMaskMasker();

    @Test
    public void shouldReturnNullForNullValue() {
        assertThat("", testee.mask(null), is(nullValue()));
    }

    @Test
    public void shouldReturnTheSourceValueUntouched() {
        String testSource = "qwertyuiop[";

        assertThat("Masked string", testee.mask(testSource), is(testSource));
    }
}
