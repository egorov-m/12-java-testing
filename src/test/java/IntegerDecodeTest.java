import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

public class IntegerDecodeTest {
    private static final Function<String, Integer> testingMethod = Integer::decode;

    @Test
    public void testEmpty() {
        Assertions.assertThrows(NumberFormatException.class, () -> {
            testingMethod.apply("");
            testingMethod.apply("0x");
            testingMethod.apply("0X");
            testingMethod.apply("#");
        });
    }

    @Test
    public void testNegativeValue() {
        Assertions.assertEquals(-1, testingMethod.apply("-1"));
        Assertions.assertEquals(-5, testingMethod.apply("-5"));
        Assertions.assertEquals(-10, testingMethod.apply("-10"));
        Assertions.assertEquals(-100, testingMethod.apply("-100"));
        Assertions.assertEquals(-1000, testingMethod.apply("-1000"));
    }

    @Test
    public void testNegativeHexValue1() {
        Assertions.assertEquals(-25, testingMethod.apply("-0x19"));
        Assertions.assertEquals(-90, testingMethod.apply("-0x5A"));
        Assertions.assertEquals(-3245, testingMethod.apply("-0xCAD"));
    }

    @Test
    public void testNegativeHexValue2() {
        Assertions.assertEquals(-25, testingMethod.apply("-0X19"));
        Assertions.assertEquals(-90, testingMethod.apply("-0X5A"));
        Assertions.assertEquals(-3245, testingMethod.apply("-0XCAD"));
    }

    @Test
    public void testNegativeHexValue3() {
        Assertions.assertEquals(-25, testingMethod.apply("-#19"));
        Assertions.assertEquals(-90, testingMethod.apply("-#5A"));
        Assertions.assertEquals(-3245, testingMethod.apply("-#CAD"));
    }

    @Test
    public void testNegativeOctalValue3() {
        Assertions.assertEquals(-27, testingMethod.apply("-033"));
        Assertions.assertEquals(-35, testingMethod.apply("-043"));
        Assertions.assertEquals(-5452, testingMethod.apply("-012514"));
    }

    @Test
    public void testNegativeWrongFormatValue() {
        Assertions.assertThrows(NumberFormatException.class, () -> {
            testingMethod.apply("--123");
            testingMethod.apply("-- 123");
            testingMethod.apply("--0x123");
            testingMethod.apply("-- 0X");
            testingMethod.apply("-- #");
        });
    }

    @Test
    public void testPositiveWrongFormatValue() {
        Assertions.assertThrows(NumberFormatException.class, () -> {
            testingMethod.apply("++123");
            testingMethod.apply("123 ++");
            testingMethod.apply("++0x123");
            testingMethod.apply("0X++");
            testingMethod.apply("++ #");
        });
    }

    @Test
    public void testPositiveValue() {
        Assertions.assertEquals(1, testingMethod.apply("1"));
        Assertions.assertEquals(1, testingMethod.apply("+1"));
        Assertions.assertEquals(10, testingMethod.apply("10"));
        Assertions.assertEquals(10, testingMethod.apply("+10"));
    }

    @Test
    public void testHexRadix1() {
        Assertions.assertEquals(25, testingMethod.apply("0x19"));
        Assertions.assertEquals(90, testingMethod.apply("0x5A"));
        Assertions.assertEquals(3245, testingMethod.apply("0xCAD"));
    }

    @Test
    public void testHexRadix2() {
        Assertions.assertEquals(25, testingMethod.apply("0X19"));
        Assertions.assertEquals(90, testingMethod.apply("0X5A"));
        Assertions.assertEquals(3245, testingMethod.apply("0XCAD"));
    }

    @Test
    public void testHexRadix3() {
        Assertions.assertEquals(25, testingMethod.apply("#19"));
        Assertions.assertEquals(90, testingMethod.apply("#5A"));
        Assertions.assertEquals(3245, testingMethod.apply("#CAD"));
    }

    @Test
    public void testOctalRadix() {
        Assertions.assertEquals(27, testingMethod.apply("033"));
        Assertions.assertEquals(35, testingMethod.apply("043"));
        Assertions.assertEquals(5452, testingMethod.apply("012514"));
    }

    @Test
    public void testLimitValue() {
        Assertions.assertEquals(Integer.MIN_VALUE, testingMethod.apply(Integer.toString(Integer.MIN_VALUE)));
        Assertions.assertEquals(Integer.MAX_VALUE, testingMethod.apply(Integer.toString(Integer.MAX_VALUE)));
    }

    @Test
    public void testRandomString() {
        Assertions.assertThrows(NumberFormatException.class, () -> {
            testingMethod.apply("Hello, World!");
            testingMethod.apply("Привет, Мир!");
            testingMethod.apply("            ");
            testingMethod.apply("~@$^*");
        });
    }

    @Test
    public void testNFE() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            testingMethod.apply(null);
        });
    }
}
