package ca.jbrains.upfp.ca.jbrains.upfp.domain.test;

import ca.jbrains.upfp.domain.Transaction;
import ca.jbrains.upfp.domain.TransactionCsvFormat;
import ca.jbrains.upfp.domain.test.TransactionBuilder;
import org.joda.time.LocalDate;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FormatTransactionAsCsvRowTest {
    @Test
    public void happyPath() throws Exception {
        final Transaction transaction = new TransactionBuilder().withDate(new LocalDate(2012, 11, 17)).withCategory("Groceries").withAmount(-1350).build();
        assertEquals("\"2012-11-17\",\"Groceries\",\"-13.50\"", format(transaction));
    }

    private String format(Transaction transaction) {
        return new TransactionCsvFormat().format(transaction);
    }
}
