package ca.jbrains.upfp.view.test;

import ca.jbrains.upfp.model.test.Transaction;
import com.google.common.base.Joiner;
import com.google.common.collect.*;
import org.junit.Test;

import java.io.*;
import java.util.*;

import static ca.jbrains.hamcrest.RegexMatcher.matches;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.junit.Assert.assertThat;

public class FormatTransactionsAsCsvFileTest {
  @Test
  public void noTransactions() throws Exception {
    final String text = formatTransactionsAsCsvFile(
        Collections.<Transaction>emptyList());
    assertThat(
        text, matches(
        "\\s*\"Date\",\\s*\"Category\"," +
        "\\s*\"Amount\"\\s*"));
    assertThat(
        text, endsWith(
        System.getProperty(
            "line.separator")));
  }

  private String formatTransactionsAsCsvFile(
      List<Transaction> transactions
  ) {
    final StringWriter text = new StringWriter();
    final PrintWriter canvas = new PrintWriter(text);
    canvas.println(formatHeader());
    return text.toString();
  }

  private String formatHeader() {
    return Joiner.on(",").join(
        Collections2.transform(
            Lists.newArrayList(
                "Date", "Category", "Amount"),
            new SurroundWithQuotes()));
  }
}
