package ca.jbrains.upfp.domain.test;

import ca.jbrains.upfp.domain.*;
import org.joda.time.LocalDate;
import org.junit.*;

import static org.junit.Assert.assertEquals;

public class FormatTransactionAsCsvRowTest {
  private LocalDate irrelevantDate;
  private String irrelevantCategoryName;
  private int irrelevantAmount;

  @Before
  public void setUp() throws Exception {
    irrelevantDate = new LocalDate(2012, 11, 17);
    irrelevantCategoryName = "irrelevant category";
    irrelevantAmount = -1350;
  }

  @Test
  public void happyPath() throws Exception {
    final Transaction transaction = new TransactionBuilder()
        .withDate(irrelevantDate).withCategory(
            irrelevantCategoryName).withAmount(
            irrelevantAmount).build();
    assertEquals(
        "\"2012-11-17\",\"irrelevant category\",\"-13.50\"",
        format(transaction));
  }

  @Test
  public void noDate() throws Exception {
    final Transaction transaction = new TransactionBuilder()
        .withCategory(irrelevantCategoryName).withAmount(
            irrelevantAmount).build();
    assertEquals(
        "\"\",\"irrelevant category\",\"-13.50\"", format(
        transaction));
  }

  @Test
  public void noCategory() throws Exception {
    final Transaction transaction = new TransactionBuilder()
        .withDate(irrelevantDate).withAmount(
            irrelevantAmount).build();
    assertEquals(
        "\"2012-11-17\",\"\",\"-13.50\"", format(
        transaction));
  }

  @Test
  public void noAmount() throws Exception {
    final Transaction transaction = new TransactionBuilder()
        .withDate(irrelevantDate).withCategory(
            irrelevantCategoryName).build();
    assertEquals(
        "\"2012-11-17\",\"irrelevant category\",\"0.00\"",
        format(transaction));
  }


  private String format(Transaction transaction) {
    return new TransactionCsvRowFormat().format(
        transaction);
  }
}
