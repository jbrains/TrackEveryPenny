package ca.jbrains.upfp.domain;

import org.joda.time.LocalDate;

public class Transaction {
    public final LocalDate date;
    public final String categoryName;
    public final int amountInCents;

    public Transaction(LocalDate date, String categoryName, int amountInCents) {
        this.date = date;
        this.categoryName = categoryName;
        this.amountInCents = amountInCents;
    }
}
