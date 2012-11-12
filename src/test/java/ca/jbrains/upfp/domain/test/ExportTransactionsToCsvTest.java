package ca.jbrains.upfp.domain.test;

import com.google.common.collect.Lists;
import org.joda.time.LocalDate;
import org.joda.time.YearMonthDay;
import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ExportTransactionsToCsvTest {
    @Test
    public void noTransactions() throws Exception {
        final StringWriter canvas = new StringWriter();

        exportTransactionsToCsv(new PrintWriter(canvas), Lists.<Transaction>newArrayList());

        assertEquals("\"Date\",\"Category\",\"Amount\"", canvas.toString().trim());
    }

    @Test
    public void oneTransaction() throws Exception {
        final StringWriter canvas = new StringWriter();

        exportTransactionsToCsv(new PrintWriter(canvas), Lists.<Transaction>newArrayList(new TransactionBuilder().withDate(date(2012,11,17)).withCategory("Groceries").withAmount(-1350).build()));

        assertEquals("\"Date\",\"Category\",\"Amount\"\n\"2012-11-17\",\"Groceries\",\"-13.50\"", canvas.toString().trim());
    }

    private LocalDate date(int year, int month, int day) {
        return new LocalDate(year, month, day);
    }

    private void exportTransactionsToCsv(PrintWriter canvas, Iterable<Transaction> transactions) {
        canvas.println("\"Date\",\"Category\",\"Amount\"");
        if (transactions.iterator().hasNext())
            canvas.println("\"2012-11-17\",\"Groceries\",\"-13.50\"");
    }

    private static class Transaction {
        public Transaction(LocalDate date, String categoryName, int amountInCents) {
        }
    }

    private static class TransactionBuilder {
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
}
