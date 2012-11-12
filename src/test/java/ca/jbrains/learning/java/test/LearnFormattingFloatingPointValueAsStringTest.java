package ca.jbrains.learning.java.test;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LearnFormattingFloatingPointValueAsStringTest {
    private String formatAsMoneyWithoutCurrencySymbol(double amount) {
        return String.format("%.2f", amount);
    }

    @Test
    public void betweenZeroAndOne() throws Exception {
        assertEquals("0.01", formatAsMoneyWithoutCurrencySymbol(0.01d));
    }

    @Test
    public void zero() throws Exception {
        assertEquals("0.00", formatAsMoneyWithoutCurrencySymbol(0d));
    }

    @Test
    public void roundedToNearestTenth() throws Exception {
        assertEquals("1.30", formatAsMoneyWithoutCurrencySymbol(1.3d));
    }

    @Test
    public void wholeDollars() throws Exception {
        assertEquals("25.00", formatAsMoneyWithoutCurrencySymbol(25d));
    }

    @Test
    public void negativeAmount() throws Exception {
        assertEquals("-12.69", formatAsMoneyWithoutCurrencySymbol(-12.69d));
    }

    @Test
    public void roundsToTwoPlaces() throws Exception {
        assertEquals("14.79", formatAsMoneyWithoutCurrencySymbol(14.785d));
        assertEquals("14.79", formatAsMoneyWithoutCurrencySymbol(14.7949999d));
    }
}
