package ca.jbrains.upfp.model.test;

import org.joda.time.LocalDate;

public class Transaction {
  private final LocalDate date;
  private final Category category;
  private final Amount amount;

  public Transaction(
      LocalDate date, Category category, Amount amount
  ) {
    if (date == null)
      throw new IllegalArgumentException(
          "A Transaction must have a date.");
    if (category == null)
      throw new IllegalArgumentException(
          "A Transaction must have a category.");
    if (amount == null)
      throw new IllegalArgumentException(
          "A Transaction must have an amount.");

    this.date = date;
    this.category = category;
    this.amount = amount;
  }

  public LocalDate getDate() {
    return date;
  }

  public Category getCategory() {
    return category;
  }

  public Amount getAmount() {
    return amount;
  }
}
