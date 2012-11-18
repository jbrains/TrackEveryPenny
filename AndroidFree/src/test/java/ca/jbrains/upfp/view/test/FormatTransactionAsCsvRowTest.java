package ca.jbrains.upfp.view.test;

import ca.jbrains.upfp.model.test.*;
import org.jmock.*;
import org.jmock.integration.junit4.JMock;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.regex.Pattern;

import static ca.jbrains.hamcrest.RegexMatcher.matches;
import static org.junit.Assert.assertThat;

@RunWith(JMock.class)
public class FormatTransactionAsCsvRowTest {
  private final Mockery mockery = new Mockery();
  private final CsvFormat<LocalDate> dateFormat = mockery
      .mock(CsvFormat.class, "date format");
  private final TransactionCsvFormat transactionCsvFormat
      = new TransactionCsvFormat(
      dateFormat, new CategoryCsvFormat(),
      new AmountCsvFormat());

  @Test
  public void happyPath() throws Exception {
    mockery.checking(
        new Expectations() {{
          allowing(dateFormat).format(
              with(
                  any(
                      LocalDate.class)));
          will(returnValue("2012-11-14"));
        }});

    final Transaction transaction = new Transaction(
        new LocalDate(2012, 11, 14), new Category(
        "Bowling Winnings"), Amount.cents(250));
    final String rowText = transactionCsvFormat
        .formatTransactionAsCsvRow(transaction);
    assertThat(
        rowText, matches(
        Pattern.compile(
            "\\s*\"2012-11-14\",\\s*\"Bowling Winnings\",\\s*\"2.50\"\\s*")));
  }
}
