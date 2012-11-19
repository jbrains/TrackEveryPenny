package ca.jbrains.upfp.test;

import ca.jbrains.upfp.model.*;
import com.google.common.collect.Lists;
import org.joda.time.LocalDate;

import java.util.List;

public class ObjectMother {
  public static Transaction createAnyNonNullTransaction() {
    return new Transaction(
        new LocalDate(2012, 8, 4), new Category(
        "irrelevant category"), Amount.cents(
        26123));
  }

  public static List<Transaction>
  anyNonEmptyListOfTransactions() {
    return Lists.newArrayList(
        createAnyNonNullTransaction(),
        createAnyNonNullTransaction(),
        createAnyNonNullTransaction());
  }
}
