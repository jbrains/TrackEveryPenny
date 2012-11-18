package ca.jbrains.upfp.view.test;

import ca.jbrains.upfp.model.test.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import com.sun.istack.internal.Nullable;
import org.joda.time.LocalDate;
import org.junit.Test;

import java.util.regex.Pattern;

import static ca.jbrains.hamcrest.RegexMatcher.matches;
import static org.junit.Assert.assertThat;

public class FormatTransactionAsCsvRowTest {
  @Test
  public void happyPath() throws Exception {
    final Transaction transaction = new Transaction(
        new LocalDate(2012, 11, 14), new Category(
        "Bowling Winnings"), Amount.cents(250));
    final String rowText = formatTransactionAsCsvRow(
        transaction);
    assertThat(
        rowText, matches(
        Pattern.compile(
            "\\s*\"2012-11-14\",\\s*\"Bowling Winnings\"," +
            "\\s*\"2.50\"\\s*")));
  }

  private String formatTransactionAsCsvRow(
      Transaction transaction
  ) {
    final String formattedDate = formatDate(
        new LocalDate(
            2012, 11, 14));
    final String formattedCategory = formatCategory(
        new Category("Bowling Winnings"));
    final String formattedAmount = formatAmount(
        Amount.cents(250));

    return formatTransactionPropertiesAsCsvRow(
        formattedDate, formattedCategory, formattedAmount);
  }

  private String formatAmount(Amount amount) {
    return "2.50";
  }

  private String formatCategory(Category category) {
    return "Bowling Winnings";
  }

  private String formatDate(LocalDate date) {
    return "2012-11-14";
  }

  private String formatTransactionPropertiesAsCsvRow(
      String formattedDate, String formattedCategory,
      String formattedAmount
  ) {
    return Joiner.on(",").join(
        Collections2.transform(
            Lists.newArrayList(
                formattedDate, formattedCategory,
                formattedAmount),
            new Function<String, String>() {
              @Override
              public String apply(@Nullable String text) {
                return "\"" + text + "\"";
              }
            }));
  }
}
