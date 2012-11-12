package ca.jbrains.upfp.domain;

import java.io.PrintWriter;

public class ExportTransactionsToCsvAction {
    private final TransactionTextFormat transactionTextFormat;

    public ExportTransactionsToCsvAction(TransactionTextFormat transactionTextFormat) {
        this.transactionTextFormat = transactionTextFormat;
    }

    public void execute(Iterable<Transaction> transactions, PrintWriter canvas) {
        // SMELL Different levels of abstraction between header and row
        // Stops this from becoming more general-purpose
        canvas.println("\"Date\",\"Category\",\"Amount\"");
        for (Transaction each : transactions) {
            canvas.println(transactionTextFormat.format(each));
        }
    }
}
