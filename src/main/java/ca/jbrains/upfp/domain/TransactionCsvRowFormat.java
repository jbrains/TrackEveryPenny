package ca.jbrains.upfp.domain;

import org.joda.time.format.DateTimeFormat;

public class TransactionCsvRowFormat
    implements TransactionTextFormat {
  @Override
  public String format(Transaction transaction) {
    final String dateText = (transaction.date == null) ? ""
                                                       : DateTimeFormat
                                                           .forPattern(
                                                               "yyyy-MM-dd")
                                                           .print(
                                                               transaction.date);
    final double amountInDollars =
        transaction.amountInCents / 100.0d;
    final String categoryText =
        (transaction.categoryName == null) ? ""
                                           : transaction.categoryName;
    return String.format(
        "\"%1$s\",\"%2$s\",\"%3$.2f\"", dateText,
        categoryText, amountInDollars);
  }
}
