package ca.jbrains.upfp.domain.test;

import ca.jbrains.upfp.domain.Transaction;
import com.google.common.collect.Lists;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Collection;

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

    public Collection<Transaction> anyValid(int howMany) {
        final ArrayList<Transaction> transactions = Lists.newArrayList();
        for (int i = 0; i < howMany; i++) {
            transactions.add(new Transaction(new LocalDate(), "irrelevant category name", 1));
        }
        return transactions;
    }
}
