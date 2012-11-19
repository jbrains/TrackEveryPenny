package ca.jbrains.upfp.model.test;

import ca.jbrains.upfp.model.Transaction;

import java.util.List;

public class InMemoryBrowseTransactionsModelTest
    extends BrowseTransactionsModelContract {
  @Override
  protected InMemoryBrowseTransactionsModel
  createBrowseTransactionModelWith(
      List<Transaction> transactions
  ) {
    return new InMemoryBrowseTransactionsModel(
        transactions);
  }

  @Override
  protected InMemoryBrowseTransactionsModel
  createBrowseTransactionsModelWhereFindAllTransactionsFailsWith(
      final RuntimeException intentionalException
  ) {

    return new InMemoryBrowseTransactionsModel(null) {
      @Override
      public List<Transaction> findAllTransactions() {
        throw intentionalException;
      }
    };
  }
}
