package ca.jbrains.upfp.test;

import ca.jbrains.upfp.BrowseTransactionsActivity;
import ca.jbrains.upfp.R;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class MyActivityTest {

    @Test
    public void shouldHaveHappySmiles() throws Exception {
        String appName = new BrowseTransactionsActivity().getResources().getString(R.string.app_name);
        assertThat(appName, equalTo("Track Every Penny"));
    }

    @Test
    public void exportToCsvButtonLaunchesExportAction() throws Exception {
        final BrowseTransactionsActivity browseTransactionsActivity = new BrowseTransactionsActivity();
        browseTransactionsActivity.onCreate(null);

        clickOn(browseTransactionsActivity, R.id.exportToCsvButton);
        // should not blow up
    }

    private void clickOn(BrowseTransactionsActivity browseTransactionsActivity, int buttonId) {
        browseTransactionsActivity.findViewById(buttonId).performClick();
    }

    @Test
    public void newTransactionOnCurrentDateButtonLaunchesAction() throws Exception {
        final BrowseTransactionsActivity browseTransactionsActivity = new BrowseTransactionsActivity();
        browseTransactionsActivity.onCreate(null);

        clickOn(browseTransactionsActivity, R.id.newTransactionOnDate);
    }

}