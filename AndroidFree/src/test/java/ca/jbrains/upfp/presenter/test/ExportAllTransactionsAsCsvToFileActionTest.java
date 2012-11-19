package ca.jbrains.upfp.presenter.test;

import ca.jbrains.upfp.model.test.Transaction;
import ca.jbrains.upfp.view.test.CsvFormat;
import com.google.common.collect.Lists;
import org.jmock.*;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.List;

@RunWith(JMock.class)
public class ExportAllTransactionsAsCsvToFileActionTest {
  private final Mockery mockery = new Mockery();
  private final CsvFormat<List<Transaction>>
      transactionsFileFormat = mockery.mock(
      CsvFormat.class, "transactions file format");
  private final WriteTextToFileAction writeTextToFileAction
      = mockery.mock(WriteTextToFileAction.class);

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

          oneOf(writeTextToFileAction).writeTextToFile(
              csvText, path);
        }});

    final List<Transaction> transactions = Lists
        .newArrayList();

    exportAllTransactionsAsCsvToFileAction(
        transactions, path);
  }

  private void exportAllTransactionsAsCsvToFileAction(
      List<Transaction> transactions, File path
  ) {
    final String text = transactionsFileFormat.format(
        transactions);
    writeTextToFileAction.writeTextToFile(text, path);
  }
}
