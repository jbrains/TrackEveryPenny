package ca.jbrains.upfp.test;

import ca.jbrains.hamcrest.RegexMatcher;
import ca.jbrains.toolkit.ProgrammerMistake;
import ca.jbrains.upfp.BrowseTransactionsActivity;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowTextView;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class AssembleEnteredTransactionTest {

    private BrowseTransactionsActivity browseTransactionsActivity;

    @Before
    public void setUp() throws Exception {
        browseTransactionsActivity = new BrowseTransactionsActivity();
        browseTransactionsActivity.onCreate(null);
    }

    @Test
    public void lookupDate() throws Exception {
        final ShadowTextView dateView = Robolectric.shadowOf(browseTransactionsActivity.dateView());
        dateView.setText("2011-11-14");

        assertEquals(new LocalDate(2011, 11, 14), browseTransactionsActivity.lookupDate());
    }

    @Test
    public void lookupDate_unparseable() throws Exception {
        // REFACTOR Poka-yoke, motherfucker.

        final ShadowTextView dateView = Robolectric.shadowOf(browseTransactionsActivity.dateView());
        dateView.setText("not a real date");

        try {
            browseTransactionsActivity.lookupDate();
            fail("How the hell did you enter a date when it's not an " +
                    "editable view?!");
        } catch (ProgrammerMistake success) {
            assertThat(success.getMessage(), RegexMatcher.matches(".*The user somehow edited the date.*"));
        }
    }
}
