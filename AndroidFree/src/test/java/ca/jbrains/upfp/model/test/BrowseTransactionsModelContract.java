package ca.jbrains.upfp.model.test;

import ca.jbrains.upfp.model.*;
import ca.jbrains.upfp.test.ObjectMother;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public abstract class BrowseTransactionsModelContract {
  @Test
  public void zeroTransactions() throws Exception {
    final BrowseTransactionsModel model
        = createBrowseTransactionModelWith(
        Lists.<Transaction>newArrayList());
    assertEquals(
        Collections.emptyList(),
        model.findAllTransactions());
    assertEquals(0, model.countTransactions());
  }

  @Test
  public void manyTransactions() throws Exception {
    final List<Transaction> transactions = ObjectMother
        .anyNonEmptyListOfTransactions();
    final InMemoryBrowseTransactionsModel model
        = createBrowseTransactionModelWith(transactions);
    assertEquals(transactions, model.findAllTransactions());
    assertEquals(3, model.countTransactions());
  }

  @Test
  public void findAllTransactionsFails() throws Exception {
    final BrowseTransactionsModel model
        = createBrowseTransactionsModelWhereFindAllTransactionsFailsWith(
        new InternalStorageException());

    try {
      model.countTransactions();
      fail(
          "How did you count the transactions when you can't find them?!");
    } catch (InternalStorageException success) {
    }
  }

  protected abstract InMemoryBrowseTransactionsModel
  createBrowseTransactionModelWith(
      List<Transaction> transactions
  );

  protected abstract InMemoryBrowseTransactionsModel
  createBrowseTransactionsModelWhereFindAllTransactionsFailsWith(
      RuntimeException intentionalException
  );
}
