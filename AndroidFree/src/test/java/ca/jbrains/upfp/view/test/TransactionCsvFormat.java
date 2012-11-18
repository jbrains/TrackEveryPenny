package ca.jbrains.upfp.view.test;

import ca.jbrains.upfp.model.test.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import com.sun.istack.internal.Nullable;
import org.joda.time.LocalDate;

public class TransactionCsvFormat {
  private final CsvFormat<LocalDate> dateCsvFormat;
  private final CsvFormat<Category> categoryCsvFormat;
  private final CsvFormat<Amount> amountCsvFormat;

  public TransactionCsvFormat(
      CsvFormat<LocalDate> dateCsvFormat,
      CsvFormat<Category> categoryCsvFormat,
      CsvFormat<Amount> amountCsvFormat
  ) {
    this.dateCsvFormat = dateCsvFormat;
    this.categoryCsvFormat = categoryCsvFormat;
    this.amountCsvFormat = amountCsvFormat;
  }

  public String formatTransactionAsCsvRow(
      Transaction transaction
  ) {
    final String formattedDate = dateCsvFormat.format(
        new LocalDate(2012, 11, 14));
    final String formattedCategory = categoryCsvFormat
        .format(
            new Category("Bowling Winnings"));
    final String formattedAmount = amountCsvFormat.format(
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