package ca.jbrains.upfp.domain.test;

import ca.jbrains.upfp.domain.Transaction;
import ca.jbrains.upfp.domain.TransactionCsvFormat;
import com.google.common.collect.Lists;
import org.joda.time.LocalDate;
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
        // SMELL Duplicates FormatTransactionAsCsvRowTest
        final StringWriter canvas = new StringWriter();

        exportTransactionsToCsv(new PrintWriter(canvas), Lists.<Transaction>newArrayList(new TransactionBuilder().withDate(new LocalDate(2012, 11, 17)).withCategory("Groceries").withAmount(-1350).build()));

        assertEquals("\"Date\",\"Category\",\"Amount\"\n\"2012-11-17\",\"Groceries\",\"-13.50\"", canvas.toString().trim());
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

    private void writeHeader(PrintWriter canvas) {
        canvas.println("\"Date\",\"Category\",\"Amount\"");
    }

}
