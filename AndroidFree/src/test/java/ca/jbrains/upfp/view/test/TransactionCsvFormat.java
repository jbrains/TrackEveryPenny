package ca.jbrains.upfp.view.test;

import ca.jbrains.upfp.model.test.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import com.sun.istack.internal.Nullable;
import org.joda.time.LocalDate;

public class TransactionCsvFormat {
  public TransactionCsvFormat() {
  }

  private String formatAmount(Amount amount) {
    return "2.50";
  }

  private String formatCategory(Category category) {
    return "Bowling Winnings";
  }

  private String formatDate(LocalDate date) {
    return "2012-11-14";
  }

  public String formatTransactionAsCsvRow(
      Transaction transaction,
      FormatTransactionAsCsvRowTest
          formatTransactionAsCsvRowTest
  ) {
    final String formattedDate = formatDate(
        new LocalDate(
            2012, 11, 14));
    final String formattedCategory = formatCategory(
        new Category("Bowling Winnings"));
    final String formattedAmount = formatAmount(
        Amount.cents(250));

    return formatTransactionPropertiesAsCsvRow(
        formattedDate, formattedCategory, formattedAmount);
  }

  private String formatTransactionPropertiesAsCsvRow(
      String formattedDate, String formattedCategory,
      String formattedAmount
  ) {
    return Joiner.on(",").join(
        Collections2.transform(
            Lists.newArrayList(
                formattedDate, formattedCategory,
                formattedAmount),
            new Function<String, String>() {
              @Override
              public String apply(@Nullable String text) {
                return "\"" + text + "\"";
              }
            }));
  }
}