package ca.jbrains.upfp.domain.test;

import ca.jbrains.upfp.domain.Transaction;
import ca.jbrains.upfp.domain.TransactionTextFormat;
import com.google.common.collect.Lists;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

@RunWith(JMock.class)
public class ExportTransactionsToCsvTest {
    private Mockery mockery = new Mockery();
    private TransactionTextFormat transactionTextFormat;
    private StringWriter contents;
    private PrintWriter canvas;

    @Before
    public void setUp() throws Exception {
        transactionTextFormat = mockery.mock(TransactionTextFormat.class);
        contents = new StringWriter();
        canvas = new PrintWriter(contents);
    }

    @Test
    public void noTransactions() throws Exception {
        exportTransactionsToCsv(canvas, transactionTextFormat, Lists.<Transaction>newArrayList());
        assertEquals("\"Date\",\"Category\",\"Amount\"", contents.toString().trim());
    }

    @Test
    public void oneTransaction() throws Exception {
        mockery.checking(new Expectations() {{
            allowing(transactionTextFormat).format(with(any(Transaction.class)));
            will(returnValue("transaction as CSV"));
        }});

        exportTransactionsToCsv(canvas, transactionTextFormat, Lists.<Transaction>newArrayList(new TransactionBuilder().withDate(new LocalDate(2012, 11, 17)).withCategory("Groceries").withAmount(-1350).build()));

        assertEquals("\"Date\",\"Category\",\"Amount\"\ntransaction as CSV", contents.toString().trim());
    }

    @Test
    public void manyTransactions() throws Exception {
        mockery.checking(new Expectations() {{
            allowing(transactionTextFormat).format(with(any(Transaction.class)));
            will(onConsecutiveCalls(returnValue("row 1 as CSV"), returnValue("row 2 as CSV"), returnValue("row 3 as CSV")));
        }});

        exportTransactionsToCsv(canvas, transactionTextFormat, new TransactionBuilder().anyValid(3));

        assertEquals("\"Date\",\"Category\",\"Amount\"\nrow 1 as CSV\nrow 2 as CSV\nrow 3 as CSV", contents.toString().trim());
    }


    private void exportTransactionsToCsv(PrintWriter canvas, TransactionTextFormat transactionTextFormat, Iterable<Transaction> transactions) throws IOException {
        canvas.println("\"Date\",\"Category\",\"Amount\"");
        for (Transaction each : transactions) {
            canvas.println(transactionTextFormat.format(each));
        }
    }

}
