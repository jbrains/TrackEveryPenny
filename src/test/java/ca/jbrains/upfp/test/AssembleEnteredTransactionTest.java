package ca.jbrains.upfp.test;

import ca.jbrains.upfp.BrowseTransactionsActivity;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowTextView;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class)
public class AssembleEnteredTransactionTest {

    private BrowseTransactionsActivity browseTransactionsActivity;

    @Before
    public void setUp() throws Exception {
        browseTransactionsActivity = new
                BrowseTransactionsActivity();
        browseTransactionsActivity.onCreate(null);
    }

    @Test
    public void lookupDate() throws Exception {
        final ShadowTextView dateView = (ShadowTextView) Robolectric.shadowOf(browseTransactionsActivity.dateView());
        dateView.setText("2011-11-14");

        assertEquals(new LocalDate(2011, 11, 14), browseTransactionsActivity.lookupDate(null));
    }

    @Test
    public void lookupDate_unparseable() throws Exception {
        final LocalDate today = new LocalDate(1974, 5, 4);

        final ShadowTextView dateView = (ShadowTextView) Robolectric.shadowOf(browseTransactionsActivity.dateView());
        dateView.setText("not a real date");

        assertEquals(today, browseTransactionsActivity.lookupDate(today));
        assertEquals(today.toString(), browseTransactionsActivity.dateView
                ().getText());
    }

}
