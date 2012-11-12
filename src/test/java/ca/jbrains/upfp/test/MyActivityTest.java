package ca.jbrains.upfp.test;

import android.widget.Button;
import ca.jbrains.upfp.MyActivity;
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
        String appName = new MyActivity().getResources().getString(R.string.app_name);
        assertThat(appName, equalTo("Track Every Penny"));
    }

    @Test
    public void exportToCsvButtonLaunchesExportAction() throws Exception {
        final MyActivity myActivity = new MyActivity();
        myActivity.onCreate(null);

        clickOn(myActivity, R.id.exportToCsvButton);
        // should not blow up
    }

    private void clickOn(MyActivity myActivity, int buttonId) {
        myActivity.findViewById(buttonId).performClick();
    }

    @Test
    public void newTransactionOnCurrentDateButtonLaunchesAction() throws Exception {
        final MyActivity myActivity = new MyActivity();
        myActivity.onCreate(null);

        clickOn(myActivity, R.id.newTransactionOnDate);
    }

}