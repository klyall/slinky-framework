//package org.slinkyframework.common.logging.mask;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.Parameterized;
//
//import java.util.Arrays;
//
//import static org.hamcrest.Matchers.is;
//import static org.junit.Assert.assertThat;
//
//@RunWith(Parameterized.class)
//public class MasksTest {
//
//    private Mask testee;
//    private String source;
//    private String expected;
//
//    public MasksTest(String scneario, Mask testee, String source, String expected) {
//        this.testee = testee;
//        this.source = source;
//        this.expected = expected;
//    }
//
//    @Parameterized.Parameters(name = "{0}")
//    public static Iterable<Object[]> data() {
//        return Arrays.asList(new Object[][] {
//                { "NO_MASK", Masks.NO_MASK, "qwertyuiop", "qwertyuiop" },
//                { "BANK_ACCOUNT_NUMBER", Masks.BANK_ACCOUNT_NUMBER, "1234564321", "******4321" },
//                { "PAYMENT_CARD_PAN", Masks.PAYMENT_CARD_PAN, "1234567890123456", "123456******3456" }
//        });
//    }
//
//    @Test
//    public void shouldFormat() {
//        assertThat("", testee.mask(source), is(expected));
//    }
//}
