package ca.jbrains.upfp.view.test;

import ca.jbrains.upfp.model.test.*;
import com.google.common.base.Joiner;
import com.google.common.collect.*;
import org.joda.time.LocalDate;

import java.util.List;

public class TransactionCsvFormat
    implements CsvFormat<Transaction> {
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

  public String format(Transaction transaction) {
    final List<String> formattedPropertiesInCorrectSequence
        = Lists.newArrayList(
        dateCsvFormat.format(
            new LocalDate(
                2012, 11, 14)), categoryCsvFormat.format(
        new Category(
            "Bowling Winnings")), amountCsvFormat.format(
        Amount.cents(250)));

    return Joiner.on(",").join(
        Collections2.transform(
            formattedPropertiesInCorrectSequence,
            SurroundWithQuotes.INSTANCE));
  }
}