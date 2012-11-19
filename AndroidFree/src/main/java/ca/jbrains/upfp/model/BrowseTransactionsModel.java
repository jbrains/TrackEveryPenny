package ca.jbrains.upfp.model;

import java.util.List;

public interface BrowseTransactionsModel {
  // CONTRACT: result >= 0
  int countTransactions();

  List<Transaction> findAllTransactions();
}
