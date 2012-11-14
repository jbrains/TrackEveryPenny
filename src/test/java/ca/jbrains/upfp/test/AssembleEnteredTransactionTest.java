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

import java.text.ParseException;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class AssembleEnteredTransactionTest {
    // REFACTOR Move semantic validation up the call stack.

    private BrowseTransactionsActivity browseTransactionsActivity;
    private boolean notifyUserInvoked = false;

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

    @Test
    public void addTransaction_InvalidCategoryName() throws Exception {
        // SMELL Overriding fixture
        // REFACTOR Separate test class
        browseTransactionsActivity = new BrowseTransactionsActivity() {
            @Override
            public int lookupAmountInCents() throws ParseException {
                // SMELL Irrelevant detail! Coupling problem.
                return 0;
            }

            @Override
            public String lookupCategoryName() {
                return "";
            }

            @Override
            public void notifyUser(String message) {
                AssembleEnteredTransactionTest.this.notifyUserInvoked = true;
                assertEquals("Category name can't be blank.", message);
            }
        };
        browseTransactionsActivity.onCreate(null);

        browseTransactionsActivity.addTransactionOnCurrentDate(null);

        assertTrue("Why did you accept a blank category name?!",
                notifyUserInvoked);
    }

}
