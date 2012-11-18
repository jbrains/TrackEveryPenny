package ca.jbrains.upfp.view.test;

import ca.jbrains.upfp.model.test.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import com.sun.istack.internal.Nullable;
import org.joda.time.LocalDate;

public class TransactionCsvFormat {

  private final TransactionCsvFormat.DateCsvFormat
      dateCsvFormat;
  private final TransactionCsvFormat.CategoryCsvFormat
      categoryCsvFormat;
  private final TransactionCsvFormat.AmountCsvFormat
      amountCsvFormat;

  public TransactionCsvFormat() {
    dateCsvFormat = new DateCsvFormat();
    categoryCsvFormat = new CategoryCsvFormat();
    amountCsvFormat = new AmountCsvFormat();
  }

  public String formatTransactionAsCsvRow(
      Transaction transaction,
      FormatTransactionAsCsvRowTest
          formatTransactionAsCsvRowTest
  ) {

    final String formattedDate = dateCsvFormat.invoke(
        new LocalDate(2012, 11, 14));
    final String formattedCategory = categoryCsvFormat
        .invoke(new Category("Bowling Winnings"));
    final String formattedAmount = amountCsvFormat.invoke(
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

  private class DateCsvFormat {
    public String invoke(LocalDate date) {
      return "2012-11-14";
    }
  }

  private class CategoryCsvFormat {
    public String invoke(Category category) {
      return "Bowling Winnings";
    }
  }

  private class AmountCsvFormat {
    public String invoke(Amount amount) {
      return "2.50";
    }
  }
}