package ca.jbrains.upfp.view.test;

import ca.jbrains.upfp.view.TransactionsCsvHeader;
import org.junit.Test;

import static ca.jbrains.hamcrest.RegexMatcher.matches;
import static org.junit.Assert.assertThat;

public class FormatTransactionCsvHeaderTest {
  @Test
  public void noTransactions() throws Exception {
    final String text = new TransactionsCsvHeader()
        .formatHeader();
    assertThat(
        text, matches(
        "\\s*\"Date\",\\s*\"Category\",\\s*\"Amount\"\\s*"));
  }
}
