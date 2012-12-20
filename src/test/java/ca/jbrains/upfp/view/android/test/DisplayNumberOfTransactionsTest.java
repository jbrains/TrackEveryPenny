package ca.jbrains.upfp.view.android.test;

import android.widget.TextView;
import ca.jbrains.toolkit.ProgrammerMistake;
import ca.jbrains.upfp.*;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class DisplayNumberOfTransactionsTest {
  @Test
  public void happyPath() throws Exception {
    final BrowseTransactionsActivity
        browseTransactionsActivity
        = new BrowseTransactionsActivity();

    browseTransactionsActivity.onCreate(null);

    browseTransactionsActivity.displayNumberOfTransactions(
        12);

    final TextView transactionsCountView
        = (TextView) browseTransactionsActivity
        .findViewById(R.id.transactionsCount);

    assertEquals(
        "12", transactionsCountView.getText().toString()
    );
  }

  @Test
  public void rejectNegativeNumber() throws Exception {
    final BrowseTransactionsActivity
        browseTransactionsActivity
        = new BrowseTransactionsActivity();

    browseTransactionsActivity.onCreate(null);

    try {
      browseTransactionsActivity
          .displayNumberOfTransactions(-1);
      fail(
          "Why did you display a negative number of transactions?! " +
          "That's crazy talk!");
    } catch (ProgrammerMistake success) {
      assertTrue(
          success.getCause()
              instanceof IllegalArgumentException);
    }
  }
}
