package ca.jbrains.upfp.model.test;

import org.joda.time.LocalDate;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateTransactionTest {
  @Test
  public void provideAllKeyInformation() throws Exception {
    final LocalDate anyNonNullDate = new LocalDate();
    final Category anyNonNullCategory = new Category(
        "irrelevant category name");
    final Amount anyNonNullAmount = Amount.cents(20);

    final Transaction transaction = new Transaction(
        anyNonNullDate, anyNonNullCategory,
        anyNonNullAmount);

    // Construction doesn't blow up
    assertEquals(anyNonNullDate, transaction.getDate());
    assertEquals(
        anyNonNullCategory, transaction.getCategory());
    assertEquals(anyNonNullAmount, transaction.getAmount());
  }
}
