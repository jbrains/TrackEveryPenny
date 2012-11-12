package ca.jbrains.upfp.domain.test;

import com.google.common.collect.Lists;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

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

        exportTransactionsToCsv(new PrintWriter(canvas), Lists.<Transaction>newArrayList(new TransactionBuilder().withDate(date(2012, 11, 17)).withCategory("Groceries").withAmount(-1350).build()));

        assertEquals("\"Date\",\"Category\",\"Amount\"\n\"2012-11-17\",\"Groceries\",\"-13.50\"", canvas.toString().trim());
    }

    private LocalDate date(int year, int month, int day) {
        return new LocalDate(year, month, day);
    }

    private void exportTransactionsToCsv(PrintWriter canvas, Iterable<Transaction> transactions) throws IOException {
        writeHeader(canvas);
        for (Transaction each : transactions) {
            writeTransaction(canvas, each);
        }
    }

    private void writeTransaction(PrintWriter canvas, Transaction each) {
        canvas.println(transactionInCsvFormat(each));
    }

    // Transaction in CSV format
    private String transactionInCsvFormat(Transaction each) {
        return new TransactionCsvFormat().format(each);
    }

    public static class TransactionCsvFormat {
        public String format(Transaction transaction) {
            final String dateText = DateTimeFormat.forPattern("yyyy-MM-dd").print(transaction.date);
            final double amountInDollars = transaction.amountInCents / 100.0d;
            return String.format("\"%1$s\",\"%2$s\",\"%3$.2f\"", dateText, transaction.categoryName, amountInDollars);
        }
    }

    private void writeHeader(PrintWriter canvas) {
        canvas.println("\"Date\",\"Category\",\"Amount\"");
    }

    public static class Transaction {
        private final LocalDate date;
        private final String categoryName;
        private final int amountInCents;

        public Transaction(LocalDate date, String categoryName, int amountInCents) {
            this.date = date;
            this.categoryName = categoryName;
            this.amountInCents = amountInCents;
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
