package ca.jbrains.upfp.view.android.test;

import android.widget.TextView;
import ca.jbrains.upfp.*;
import ca.jbrains.upfp.view.BrowseTransactionsView;
import ca.jbrains.upfp.view.android
    .AndroidBrowseTransactionsView;
import ca.jbrains.upfp.view.test
    .BrowseTransactionsViewContract;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class DisplayNumberOfTransactionsTest
    extends BrowseTransactionsViewContract {
  @Test
  public void happyPath() throws Exception {
    final BrowseTransactionsActivity
        browseTransactionsActivity
        = new BrowseTransactionsActivity();
    browseTransactionsActivity.onCreate(null);
    final TextView transactionsCountView
        = (TextView) browseTransactionsActivity
        .findViewById(R.id.transactionsCount);
    final AndroidBrowseTransactionsView
        androidBrowseTransactionsView
        = new AndroidBrowseTransactionsView(
        transactionsCountView);

    androidBrowseTransactionsView
        .displayNumberOfTransactions(12);

    assertEquals(
        "12", transactionsCountView.getText().toString());
  }

  @Override
  protected BrowseTransactionsView initializeView() {
    return new AndroidBrowseTransactionsView(null);
  }
}
