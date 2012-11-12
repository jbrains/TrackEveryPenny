package ca.jbrains.upfp.domain.test;

import ca.jbrains.upfp.domain.Transaction;
import ca.jbrains.upfp.domain.TransactionCsvRowFormat;
import ca.jbrains.upfp.domain.TransactionTextFormat;
import com.google.common.collect.Lists;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

@RunWith(JMock.class)
public class ExportTransactionsToCsvTest {
    private Mockery mockery = new Mockery();

    @Test
    public void noTransactions() throws Exception {
        final StringWriter canvas = new StringWriter();

        exportTransactionsToCsv(new PrintWriter(canvas), new TransactionCsvRowFormat(), Lists.<Transaction>newArrayList());

        assertEquals("\"Date\",\"Category\",\"Amount\"", canvas.toString().trim());
    }

    @Test
    public void oneTransaction() throws Exception {
        // SMELL Duplicates FormatTransactionAsCsvRowTest
        final StringWriter canvas = new StringWriter();

        exportTransactionsToCsv(new PrintWriter(canvas), new TransactionCsvRowFormat(), Lists.<Transaction>newArrayList(new TransactionBuilder().withDate(new LocalDate(2012, 11, 17)).withCategory("Groceries").withAmount(-1350).build()));

        assertEquals("\"Date\",\"Category\",\"Amount\"\n\"2012-11-17\",\"Groceries\",\"-13.50\"", canvas.toString().trim());
    }

    @Test
    public void manyTransactions() throws Exception {
        final StringWriter canvas = new StringWriter();

        final TransactionTextFormat transactionTextFormat = mockery.mock(TransactionTextFormat.class);

        mockery.checking(new Expectations() {{
            allowing(transactionTextFormat).format(with(any(Transaction.class)));
            will(onConsecutiveCalls(returnValue("row 1 as CSV"), returnValue("row 2 as CSV"), returnValue("row 3 as CSV")));
        }});

        exportTransactionsToCsv(new PrintWriter(canvas), transactionTextFormat, new TransactionBuilder().anyValid(3));

        assertEquals("\"Date\",\"Category\",\"Amount\"\nrow 1 as CSV\nrow 2 as CSV\nrow 3 as CSV", canvas.toString().trim());
    }


    private void exportTransactionsToCsv(PrintWriter canvas, TransactionTextFormat transactionTextFormat, Iterable<Transaction> transactions) throws IOException {
        writeHeader(canvas);
        for (Transaction each : transactions) {
            canvas.println(transactionTextFormat.format(each));
        }
    }

    private void writeHeader(PrintWriter canvas) {
        canvas.println("\"Date\",\"Category\",\"Amount\"");
    }

}
