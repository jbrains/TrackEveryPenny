package ca.jbrains.upfp.model.test;

import ca.jbrains.upfp.mvp.BrowseTransactionsModel;

import java.util.Collection;

public class InMemoryBrowseTransactionsModel
    implements BrowseTransactionsModel {
  private final Collection<Object> transactions;

  public InMemoryBrowseTransactionsModel(
      Collection<Object> transactions
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
  public Collection<Object> findAllTransactions() {
    return transactions;
  }
}
