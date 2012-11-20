package ca.jbrains.upfp.presenter;

import ca.jbrains.upfp.model.Transaction;
import ca.jbrains.upfp.view.CsvFormat;

import java.io.IOException;
import java.util.List;

public class ExportAllTransactionsAsCsvToFileAction
    implements ExportAllTransactionsAction {
  private final CsvFormat<List<Transaction>>
      transactionsFileFormat;
  private WriteTextAction writeTextAction;

  public ExportAllTransactionsAsCsvToFileAction(
      CsvFormat<List<Transaction>> transactionsFileFormat,
      WriteTextAction writeTextAction
  ) {
    this.transactionsFileFormat = transactionsFileFormat;
    this.writeTextAction = writeTextAction;
  }

  public void execute(List<Transaction> transactions)
      throws IOException {
    final String text = transactionsFileFormat.format(
        transactions);
    writeTextAction.writeText(text);
  }
}
