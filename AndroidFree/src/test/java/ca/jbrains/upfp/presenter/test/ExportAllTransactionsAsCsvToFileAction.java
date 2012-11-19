package ca.jbrains.upfp.presenter.test;

import ca.jbrains.upfp.model.test.Transaction;
import ca.jbrains.upfp.view.test.CsvFormat;

import java.io.*;
import java.util.List;

public class ExportAllTransactionsAsCsvToFileAction {
  private final CsvFormat<List<Transaction>>
      transactionsFileFormat;
  private final WriteTextToFileAction writeTextToFileAction;
  private final File destinationFile;
  private WriteTextAction writeTextAction;

  public ExportAllTransactionsAsCsvToFileAction(
      CsvFormat<List<Transaction>> transactionsFileFormat,
      final WriteTextToFileAction writeTextToFileAction,
      final File destinationFile
  ) {
    this.transactionsFileFormat = transactionsFileFormat;
    this.writeTextToFileAction = writeTextToFileAction;
    this.destinationFile = destinationFile;
    writeTextAction = new WriteTextAction() {
      @Override
      public void writeText(String text)
          throws IOException {
        writeTextToFileAction.writeTextToFile(
            text, destinationFile);
      }
    };
  }

  public void exportAllTransactionsAsCsvToFileAction(
      List<Transaction> transactions
  ) throws IOException {
    final String text = transactionsFileFormat.format(
        transactions);
    writeTextAction.writeText(text);
  }
}
