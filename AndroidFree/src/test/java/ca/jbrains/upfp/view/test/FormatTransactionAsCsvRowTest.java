package ca.jbrains.upfp.view.test;

import ca.jbrains.upfp.model.test.*;
import org.joda.time.LocalDate;
import org.junit.Test;

import java.util.regex.Pattern;

import static ca.jbrains.hamcrest.RegexMatcher.matches;
import static org.junit.Assert.assertThat;

public class FormatTransactionAsCsvRowTest {
  private final TransactionCsvFormat transactionCsvFormat
      = new TransactionCsvFormat(
      new TransactionCsvFormat.DateCsvFormat(),
      new TransactionCsvFormat.CategoryCsvFormat(),
      new TransactionCsvFormat.AmountCsvFormat());

  @Test
  public void happyPath() throws Exception {
    final Transaction transaction = new Transaction(
        new LocalDate(2012, 11, 14), new Category(
        "Bowling Winnings"), Amount.cents(250));
    final String rowText = transactionCsvFormat
        .formatTransactionAsCsvRow(
            transaction);
    assertThat(
        rowText, matches(
        Pattern.compile(
            "\\s*\"2012-11-14\",\\s*\"Bowling Winnings\",\\s*\"2.50\"\\s*")));
  }
}
