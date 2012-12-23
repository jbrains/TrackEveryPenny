package ca.jbrains.upfp.mvp;

public interface BrowseTransactionsModel {
  // CONTRACT: result >= 0
  int countTransactions();
}
