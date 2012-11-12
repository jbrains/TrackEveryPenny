package ca.jbrains.upfp.domain.test;

import ca.jbrains.upfp.domain.Transaction;
import org.joda.time.LocalDate;

public class TransactionBuilder {
    private LocalDate date;
    private String categoryName;
    private int amountInCents;

    public TransactionBuilder withDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public TransactionBuilder withCategory(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public TransactionBuilder withAmount(int amountInCents) {
        this.amountInCents = amountInCents;
        return this;
    }

    public Transaction build() {
        return new Transaction(date, categoryName, amountInCents);
    }
}
