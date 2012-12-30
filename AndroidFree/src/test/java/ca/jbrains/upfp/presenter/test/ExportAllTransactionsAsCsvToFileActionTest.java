package ca.jbrains.upfp.presenter.test;

import ca.jbrains.upfp.model.test.Transaction;
import ca.jbrains.upfp.view.test.CsvFormat;
import com.google.common.collect.Lists;
import org.jmock.*;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.*;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(JMock.class)
public class ExportAllTransactionsAsCsvToFileActionTest {
  private final Mockery mockery = new Mockery();
  private final CsvFormat<List<Transaction>>
      transactionsFileFormat = mockery.mock(
      CsvFormat.class, "transactions file format");
  private final WriteTextAction writeTextAction = mockery
      .mock(WriteTextAction.class);
  private final ExportAllTransactionsAsCsvToFileAction
      exportAllTransactionsAsCsvToFileAction
      = new ExportAllTransactionsAsCsvToFileAction(
      transactionsFileFormat, writeTextAction);

  @Test
  public void happyPath() throws Exception {
    final String csvText = "::the transactions as CSV::";
    final File path = new File("/irrelevant/path");

    mockery.checking(
        new Expectations() {{
          allowing(transactionsFileFormat).format(
              with(
                  any(
                      List.class)));
          will(returnValue(csvText));

          oneOf(writeTextAction).writeText(csvText);
        }});

    final List<Transaction> transactions = Lists
        .newArrayList();

    exportAllTransactionsAsCsvToFileAction
        .exportAllTransactionsAsCsvToFileAction(
            transactions);
  }

  @Test
  public void writingToFileFails() throws Exception {
    final IOException ioFailure = new IOException(
        "You probably ran out of disk space.");

    mockery.checking(
        new Expectations() {{
          ignoring(transactionsFileFormat);

          allowing(writeTextAction).writeText(
              with(
                  any(
                      String.class)));
          will(throwException(ioFailure));
        }});

    final List<Transaction> irrelevantTransactions = Lists
        .newArrayList();
    try {
      exportAllTransactionsAsCsvToFileAction
          .exportAllTransactionsAsCsvToFileAction(
              irrelevantTransactions);
      fail(
          "Writing text to disk failed, but you don't care?!");
    } catch (IOException success) {
      assertSame(ioFailure, success);
    }
  }
}
