package ca.jbrains.upfp.presenter;

import ca.jbrains.upfp.model.Transaction;

import java.io.IOException;
import java.util.List;

public interface ExportAllTransactionsAction {
  void execute(List<Transaction> transactions)
      throws IOException;
}
