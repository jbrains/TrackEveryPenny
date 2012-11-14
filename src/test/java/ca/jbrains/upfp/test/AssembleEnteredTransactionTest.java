package ca.jbrains.upfp.test;

import ca.jbrains.upfp.BrowseTransactionsActivity;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowTextView;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class AssembleEnteredTransactionTest {
    @Test
    public void lookupDate() throws Exception {
        final BrowseTransactionsActivity browseTransactionsActivity = new
                BrowseTransactionsActivity();
        browseTransactionsActivity.onCreate(null);
        final ShadowTextView dateView = (ShadowTextView) Robolectric.shadowOf(browseTransactionsActivity.dateView());
        dateView.setText("2011-11-14");

        assertEquals(new LocalDate(2011, 11, 14), browseTransactionsActivity.lookupDate());
    }

}
