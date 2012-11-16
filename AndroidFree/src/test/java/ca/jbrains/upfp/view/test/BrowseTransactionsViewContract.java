package ca.jbrains.upfp.view.test;

import ca.jbrains.toolkit.ProgrammerMistake;
import ca.jbrains.upfp.view.BrowseTransactionsView;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class BrowseTransactionsViewContract {
  @Test
  public void rejectNegativeNumber() throws Exception {
    final BrowseTransactionsView browseTransactionsView
        = initializeView();

    try {
      browseTransactionsView
          .displayNumberOfTransactions(-1);

      fail(
          "Why did you display a negative number of " +
          "transactions?! That's crazy talk!");
    } catch (ProgrammerMistake success) {
      assertTrue(
          success.getCause()
              instanceof IllegalArgumentException);
    }
  }

  protected abstract BrowseTransactionsView initializeView();
}
