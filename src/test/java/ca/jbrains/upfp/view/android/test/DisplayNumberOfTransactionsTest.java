package ca.jbrains.upfp.view.android.test;

import android.widget.TextView;
import ca.jbrains.upfp.BrowseTransactionsActivity;
import ca.jbrains.upfp.R;
import ca.jbrains.upfp.view.BrowseTransactionsView;
import ca.jbrains.upfp.view.test.BrowseTransactionsViewContract;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class DisplayNumberOfTransactionsTest extends BrowseTransactionsViewContract {
    @Test
    public void happyPath() throws Exception {
        final BrowseTransactionsActivity browseTransactionsActivity = new BrowseTransactionsActivity();
        browseTransactionsActivity.onCreate(null);

        browseTransactionsActivity.displayNumberOfTransactions(12);

        final TextView transactionsCountView = (TextView) browseTransactionsActivity
                .findViewById(R.id.transactionsCount);
        assertEquals("12", transactionsCountView.getText().toString());
    }

    @Override
    protected BrowseTransactionsView initializeView() {
        final BrowseTransactionsActivity browseTransactionsActivity = new BrowseTransactionsActivity();
        browseTransactionsActivity.onCreate(null);
        return browseTransactionsActivity;
    }
}
