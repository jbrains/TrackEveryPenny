package ca.jbrains.upfp.model.test;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

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
}
