package ca.jbrains.upfp.model.test;

import ca.jbrains.upfp.model.Amount;
import org.junit.Test;

import static org.junit.Assert.*;

public class AmountValueObjectBehaviorTest {
  @Test
  public void reflexive() throws Exception {
    final Amount amount = Amount.cents(12);

    assertEquals(amount, amount);
  }

  @Test
  public void symmetric() throws Exception {
    final Amount equalAmount = Amount.cents(20);
    final Amount anotherEqualAmount = Amount.cents(20);
    final Amount unequalAmount = Amount.cents(21);

    assertEquals(equalAmount, anotherEqualAmount);
    assertEquals(anotherEqualAmount, equalAmount);

    assertFalse(equalAmount.equals(unequalAmount));
    assertFalse(unequalAmount.equals(equalAmount));
  }

  @Test
  public void transitive() throws Exception {
    final Amount equalAmount1 = Amount.cents(32);
    final Amount equalAmount2 = Amount.cents(32);
    final Amount equalAmount3 = Amount.cents(32);
    final Amount unequalAmount = Amount.cents(31);

    assertEquals(equalAmount1, equalAmount2);
    assertEquals(equalAmount2, equalAmount3);
    assertEquals(equalAmount1, equalAmount3);

    assertFalse(unequalAmount.equals(equalAmount1));
    assertFalse(unequalAmount.equals(equalAmount2));
  }

  @Test
  public void nothingEqualsNull() throws Exception {
    assertFalse(Amount.cents(0).equals(null));
  }

  // I won't bother testing for consistency as long as
  // Amount is immutable and final.
}
