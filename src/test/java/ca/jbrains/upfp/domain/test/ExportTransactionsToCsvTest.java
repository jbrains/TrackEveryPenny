package ca.jbrains.upfp.domain.test;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ExportTransactionsToCsvTest {
    @Test
    public void noTransactions() throws Exception {
        final StringWriter canvas = new StringWriter();

        exportTransactionsToCsv(new PrintWriter(canvas), Lists.<Transaction> newArrayList());

        assertEquals("", canvas.toString().trim());
    }

    private void exportTransactionsToCsv(PrintWriter canvas, Iterable<Transaction> transactions) {
        canvas.println();
    }

    private static class Transaction {
    }
}
