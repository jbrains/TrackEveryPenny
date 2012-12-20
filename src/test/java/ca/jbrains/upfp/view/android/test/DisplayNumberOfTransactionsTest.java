package ca.jbrains.upfp.view.android.test;

import android.widget.TextView;
import ca.jbrains.upfp.*;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

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
}
