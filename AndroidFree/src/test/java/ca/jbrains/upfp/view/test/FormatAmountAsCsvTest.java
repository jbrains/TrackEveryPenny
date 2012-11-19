package ca.jbrains.upfp.view.test;

import ca.jbrains.upfp.model.Amount;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FormatAmountAsCsvTest {
  @Test
  public void noZeroes() throws Exception {
    assertEquals("12.87", formatCents(1287));
  }

  @Test
  public void oneTrailingZero() throws Exception {
    assertEquals("12.50", formatCents(1250));
  }

  @Test
  public void twoTrailingZeroes() throws Exception {
    assertEquals("89.00", formatCents(8900));
  }

  @Test
  public void lessThanOneDollar() throws Exception {
    assertEquals("0.98", formatCents(98));
  }

  @Test
  public void lessThanTenCents() throws Exception {
    assertEquals("0.01", formatCents(1));
  }

  @Test
  public void zero() throws Exception {
    assertEquals("0.00", formatCents(0));
  }

  @Test
  public void negative() throws Exception {
    assertEquals("-0.07", formatCents(-7));
  }

  @Test
  public void thousands() throws Exception {
    assertEquals("1579.23", formatCents(157923));
  }

  private String formatCents(int cents) {
    return new AmountCsvFormat().format(
        Amount.cents(cents));
  }
}
