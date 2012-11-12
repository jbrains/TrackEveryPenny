package ca.jbrains.upfp.test;

import android.view.View;
import android.widget.Button;
import ca.jbrains.upfp.MyActivity;
import ca.jbrains.upfp.R;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowAbsListView;
import com.xtremelabs.robolectric.shadows.ShadowView;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        final Button exportToCsvButton = (Button) myActivity.findViewById(R.id.exportToCsvButton);
        exportToCsvButton.performClick();
        // should not blow up
    }
}