package ca.jbrains.upfp.model.test;

import ca.jbrains.upfp.model.InternalStorageException;
import ca.jbrains.upfp.mvp.BrowseTransactionsModel;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class InMemoryBrowseTransactionsModelTest {
  @Test
  public void zeroTransactions() throws Exception {
    final BrowseTransactionsModel model
        = createBrowseTransactionModelWith(
        Lists.newArrayList());
    assertEquals(
        Collections.emptyList(),
        model.findAllTransactions());
    assertEquals(0, model.countTransactions());
  }

  @Test
  public void manyTransactions() throws Exception {
    final Collection<Object> transactions = Lists
        .newArrayList(
            new Object(), new Object(), new Object());
    final InMemoryBrowseTransactionsModel model
        = createBrowseTransactionModelWith(transactions);
    assertEquals(transactions, model.findAllTransactions());
    assertEquals(3, model.countTransactions());
  }

  @Test
  public void findAllTransactionsFails() throws Exception {
    final BrowseTransactionsModel model
        = createFailingBrowseTransactionsModel(
        new InternalStorageException());

    try {
      model.countTransactions();
      fail(
          "How did you count the transactions when you can't find them?!");
    } catch (InternalStorageException success) {
    }
  }

  private InMemoryBrowseTransactionsModel createBrowseTransactionModelWith(
      Collection<Object> transactions
  ) {
    return new InMemoryBrowseTransactionsModel(
        transactions);
  }

  private InMemoryBrowseTransactionsModel createFailingBrowseTransactionsModel(
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
