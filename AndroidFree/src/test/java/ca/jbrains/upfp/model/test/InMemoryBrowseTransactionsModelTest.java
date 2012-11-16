package ca.jbrains.upfp.model.test;

import ca.jbrains.upfp.model.InternalStorageException;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class InMemoryBrowseTransactionsModelTest {
  @Test
  public void zeroTransactions() throws Exception {
    final InMemoryBrowseTransactionsModel model
        = new InMemoryBrowseTransactionsModel(
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
        = new InMemoryBrowseTransactionsModel(transactions);
    assertEquals(transactions, model.findAllTransactions());
    assertEquals(3, model.countTransactions());
  }

  @Test
  public void findAllTransactionsFails() throws Exception {
    final InMemoryBrowseTransactionsModel model
        = new InMemoryBrowseTransactionsModel(null) {
      @Override
      public Collection<Object> findAllTransactions() {
        throw new InternalStorageException();
      }
    };

    try {
      model.countTransactions();
      fail(
          "How did you count the transactions when you can't find them?!");
    } catch (InternalStorageException success) {
    }
  }
}
