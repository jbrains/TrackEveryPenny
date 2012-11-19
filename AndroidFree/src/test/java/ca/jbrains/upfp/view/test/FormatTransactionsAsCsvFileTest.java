package ca.jbrains.upfp.view.test;

import ca.jbrains.upfp.model.test.*;
import com.google.common.base.Joiner;
import com.google.common.collect.*;
import org.jmock.*;
import org.jmock.integration.junit4.JMock;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.*;
import java.util.*;

import static ca.jbrains.hamcrest.RegexMatcher.matches;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.junit.Assert.*;

@RunWith(JMock.class)
public class FormatTransactionsAsCsvFileTest {
  private Mockery mockery = new Mockery();

  private final CsvFormat<Transaction> transactionCsvFormat
      = mockery.mock(CsvFormat.class, "transaction format");

  @Test
  public void noTransactions() throws Exception {
    final String text = formatTransactionsAsCsvFile(
        Collections.<Transaction>emptyList(),
        transactionCsvFormat);
    assertThat(
        text, matches(
        "\\s*\"Date\",\\s*\"Category\"," +
        "\\s*\"Amount\"\\s*"));
    assertThat(
        text, endsWith(
        System.getProperty(
            "line.separator")));
  }

  @Test
  public void aFewTransactions() throws Exception {
    mockery.checking(
        new Expectations() {{
          allowing(transactionCsvFormat).format(
              with(
                  any(
                      Transaction.class)));
          will(
              onConsecutiveCalls(
                  returnValue("::row 1::"), returnValue(
                  "::row 2::"), returnValue("::row 3::")));
        }});

    final String text = formatTransactionsAsCsvFile(
        Lists.newArrayList(
            createAnyNonNullTransaction(),
            createAnyNonNullTransaction(),
            createAnyNonNullTransaction()),
        transactionCsvFormat);

    final List<String> lines = Arrays.asList(
        text.split(
            System.getProperty("line.separator")));
    assertEquals(4, lines.size());
    assertThat(
        lines.get(0), matches(
        "\\s*\"Date\",\\s*\"Category\"," +
        "\\s*\"Amount\"\\s*"));
    assertThat(lines.get(1), matches("::row 1::"));
    assertThat(lines.get(2), matches("::row 2::"));
    assertThat(lines.get(3), matches("::row 3::"));
    assertThat(
        text, endsWith(
        System.getProperty(
            "line.separator")));
  }

  private Transaction createAnyNonNullTransaction() {
    return new Transaction(
        new LocalDate(2012, 8, 4), new Category(
        "irrelevant category"), Amount.cents(
        26123));
  }

  private String formatTransactionsAsCsvFile(
      List<Transaction> transactions,
      CsvFormat<Transaction> transactionCsvFormat
  ) {
    // I'm not sure whether I prefer this to join on line
    // .separator
    final StringWriter text = new StringWriter();
    final PrintWriter canvas = new PrintWriter(text);
    canvas.println(formatHeader());
    for (Transaction each : transactions) {
      canvas.println(transactionCsvFormat.format(each));
    }
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
