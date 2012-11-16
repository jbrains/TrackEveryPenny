package ca.jbrains.upfp.model.test;

import java.util.Collection;

public class InMemoryBrowseTransactionsModelTest
    extends BrowseTransactionsModelContract {
  @Override
  protected InMemoryBrowseTransactionsModel createBrowseTransactionModelWith(
      Collection<Object> transactions
  ) {
    return new InMemoryBrowseTransactionsModel(
        transactions);
  }

  @Override
  protected InMemoryBrowseTransactionsModel createFailingBrowseTransactionsModel(
      final RuntimeException intentionalException
  ) {

    return new InMemoryBrowseTransactionsModel(null) {
      @Override
      public Collection<Object> findAllTransactions() {
        throw intentionalException;
      }
    };
  }
}
