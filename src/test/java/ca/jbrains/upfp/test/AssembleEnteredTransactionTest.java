package ca.jbrains.upfp.test;

import ca.jbrains.hamcrest.RegexMatcher;
import ca.jbrains.toolkit.ProgrammerMistake;
import ca.jbrains.upfp.BrowseTransactionsActivity;
import ca.jbrains.upfp.R;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowTextView;
import org.joda.time.LocalDate;
import org.junit.Assert;
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

    @Test
    public void lookupAmount_HappyPath() throws Exception {
        final ShadowTextView amountView = (ShadowTextView) Robolectric.shadowOf
                (browseTransactionsActivity.findViewById(R.id
                        .amount));
        amountView.setText("15.00");

        final int amountInCents = browseTransactionsActivity.lookupAmountInCents();

        assertEquals(1500, amountInCents);
    }

    @Test
    public void lookupAmount_TooManyDecimalPlaces() throws Exception {
        // REFACTOR Move this into a filter. See http://stackoverflow.com/questions/5357455/limit-decimal-places-in-android-edittext
        final ShadowTextView amountView = (ShadowTextView) Robolectric.shadowOf
                (browseTransactionsActivity.findViewById(R.id
                        .amount));
        amountView.setText("1.789");

        final int amountInCents = browseTransactionsActivity.lookupAmountInCents();

        assertEquals(179, amountInCents);
        // SMELL We need to do this, but it feels like it's happening in the
        // wrong place
        assertEquals("1.79", browseTransactionsActivity.amountView().getText().toString());
    }

    @Test
    public void lookupCategoryName_HappyPath() throws Exception {
        browseTransactionsActivity.categoryView().setText("Bowling Winnings");
        assertEquals("Bowling Winnings", browseTransactionsActivity.lookupCategoryName());
    }
}
