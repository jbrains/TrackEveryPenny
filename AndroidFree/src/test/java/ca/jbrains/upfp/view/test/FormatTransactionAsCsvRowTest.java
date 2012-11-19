package ca.jbrains.upfp.view.test;

import ca.jbrains.toolkit.ProgrammerMistake;
import ca.jbrains.upfp.model.*;
import org.jmock.*;
import org.jmock.integration.junit4.JMock;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.regex.Pattern;

import static ca.jbrains.hamcrest.RegexMatcher.matches;
import static org.junit.Assert.*;

@RunWith(JMock.class)
public class FormatTransactionAsCsvRowTest {
  private final Mockery mockery = new Mockery();

  private final CsvFormat<LocalDate> dateFormat = mockery
      .mock(
          CsvFormat.class, "date format");
  private final CsvFormat<Category> categoryFormat = mockery
      .mock(
          CsvFormat.class, "category format");
  private final CsvFormat<Amount> amountFormat = mockery
      .mock(
          CsvFormat.class, "amount format");
  private final TransactionCsvFormat transactionCsvFormat
      = new TransactionCsvFormat(
      dateFormat, categoryFormat, amountFormat);

  private LocalDate anyNonNullDate = new LocalDate(
      2012, 11, 14);
  private Category anyNonNullCategory = new Category(
      "Bowling Winnings");
  private Amount anyNonNullAmount = Amount.cents(250);

  @Test
  public void happyPath() throws Exception {
    mockery.checking(
        new Expectations() {{
          allowing(dateFormat).format(
              with(
                  any(
                      LocalDate.class)));
          will(returnValue("::the date::"));

          allowing(categoryFormat).format(
              with(
                  any(
                      Category.class)));
          will(returnValue("::the category::"));

          allowing(amountFormat).format(
              with(
                  any(
                      Amount.class)));
          will(returnValue("::the amount::"));
        }});

    final Transaction transaction = new Transaction(
        anyNonNullDate, anyNonNullCategory,
        anyNonNullAmount);
    final String rowText = transactionCsvFormat.format(
        transaction);
    assertThat(
        rowText, matches(
        Pattern.compile(
            "\\s*\"::the date::\"," +
            "\\s*\"::the category::\"," +
            "\\s*\"::the amount::\"\\s*")));
  }

  @Test
  public void nullTransaction() throws Exception {
    try {
      transactionCsvFormat.format(null);
      fail("How did you format a null transaction?!");
    } catch (ProgrammerMistake success) {
    }
  }

}
