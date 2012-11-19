package ca.jbrains.upfp.model.test;

import ca.jbrains.upfp.model.*;

import java.util.List;

public class InMemoryBrowseTransactionsModel
    implements BrowseTransactionsModel {
  private final List<Transaction> transactions;

  public InMemoryBrowseTransactionsModel(
      List<Transaction> transactions
  ) {
    this.transactions = transactions;
  }

  @Override
  public int countTransactions() {
    // Invoking findAllTransactions() instead of using the
    // field directly ensures that the model throws
    // exceptions consistently when it fails.
    return findAllTransactions().size();
  }

  @Override
  public List<Transaction> findAllTransactions() {
    return transactions;
  }
}
