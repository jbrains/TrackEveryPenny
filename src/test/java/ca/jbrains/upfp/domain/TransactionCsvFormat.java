package ca.jbrains.upfp.domain;

import org.joda.time.format.DateTimeFormat;

public class TransactionCsvFormat {
    public String format(Transaction transaction) {
        final String dateText = DateTimeFormat.forPattern("yyyy-MM-dd").print(transaction.date);
        final double amountInDollars = transaction.amountInCents / 100.0d;
        return String.format("\"%1$s\",\"%2$s\",\"%3$.2f\"", dateText, transaction.categoryName, amountInDollars);
    }
}
