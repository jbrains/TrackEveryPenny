package ca.jbrains.upfp.view.test;

import ca.jbrains.upfp.Conveniences;
import ca.jbrains.upfp.model.test.Transaction;
import com.google.common.base.*;
import com.google.common.collect.*;
import com.sun.istack.internal.Nullable;

import java.util.List;

public class TransactionsCsvFileFormat {
  public TransactionsCsvFileFormat() {
  }

  // REFACTOR Parameterise this in terms of Transaction
  public String formatTransactionsAsCsvFile(
      List<Transaction> transactions,
      CsvHeaderFormat csvHeaderFormat,
      final CsvFormat<Transaction> transactionCsvFormat
  ) {
    final List<String> lines = Lists.newArrayList(
        csvHeaderFormat.formatHeader());
    lines.addAll(
        Collections2.transform(
            transactions,
            new Function<Transaction, String>() {
              @Override
              public String apply(
                  @Nullable Transaction transaction
              ) {
                return transactionCsvFormat.format(
                    transaction);
              }
            }));

    return Joiner.on(Conveniences.NEWLINE).join(lines)
        .concat(Conveniences.NEWLINE);
  }
}