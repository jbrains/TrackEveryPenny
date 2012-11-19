package ca.jbrains.upfp.view.test;

import ca.jbrains.upfp.Conveniences;
import ca.jbrains.upfp.model.test.*;
import com.google.common.base.*;
import com.google.common.collect.Lists;
import com.sun.istack.internal.Nullable;
import org.jmock.*;
import org.jmock.integration.junit4.JMock;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

import static ca.jbrains.hamcrest.RegexMatcher.matches;
import static com.google.common.collect.Collections2
    .transform;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.junit.Assert.*;

@RunWith(JMock.class)
public class FormatTransactionsAsCsvFileTest {
  private Mockery mockery = new Mockery();

  private final CsvFormat<Transaction> transactionCsvFormat
      = mockery.mock(CsvFormat.class, "transaction format");
  private final CsvHeaderFormat csvHeaderFormat = mockery
      .mock(
          CsvHeaderFormat.class, "header format");

  @Test
  public void noTransactions() throws Exception {
    mockery.checking(
        new Expectations() {{
          allowing(csvHeaderFormat).formatHeader();
          will(returnValue("::header::"));
        }});

    final String text = formatTransactionsAsCsvFile(
        Collections.<Transaction>emptyList(),
        csvHeaderFormat, transactionCsvFormat);
    final List<String> lines = Arrays.asList(
        text.split(
            Conveniences.NEWLINE));
    assertEquals(1, lines.size());
    assertThat(lines.get(0), matches("::header::"));
    assertThat(text, endsWith(Conveniences.NEWLINE));
  }

  @Test
  public void aFewTransactions() throws Exception {
    mockery.checking(
        new Expectations() {{
          allowing(csvHeaderFormat).formatHeader();
          will(returnValue("::header::"));
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
            createAnyNonNullTransaction()), csvHeaderFormat,
        transactionCsvFormat);

    final List<String> lines = Arrays.asList(
        text.split(
            Conveniences.NEWLINE));
    assertEquals(4, lines.size());
    assertThat(lines.get(0), matches("::header::"));
    assertThat(lines.get(1), matches("::row 1::"));
    assertThat(lines.get(2), matches("::row 2::"));
    assertThat(lines.get(3), matches("::row 3::"));
    assertThat(text, endsWith(Conveniences.NEWLINE));
  }

  private Transaction createAnyNonNullTransaction() {
    return new Transaction(
        new LocalDate(2012, 8, 4), new Category(
        "irrelevant category"), Amount.cents(
        26123));
  }

  // REFACTOR Parameterise this in terms of Transaction
  private String formatTransactionsAsCsvFile(
      List<Transaction> transactions,
      CsvHeaderFormat csvHeaderFormat,
      final CsvFormat<Transaction> transactionCsvFormat
  ) {
    final List<String> lines = Lists.newArrayList(
        csvHeaderFormat.formatHeader());
    lines.addAll(
        transform(
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
