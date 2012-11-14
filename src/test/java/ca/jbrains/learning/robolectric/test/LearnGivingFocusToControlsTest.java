package ca.jbrains.learning.robolectric.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class LearnGivingFocusToControlsTest {
    @Test
    public void itLetsUsCheckFocus() throws Exception {
        class TwoViews extends Activity {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                final TextView firstTextView = new TextView(this);
                firstTextView.setId(1);
                final TextView secondTextView = new TextView(this);
                secondTextView.setId(2);
                final LinearLayout layout = new LinearLayout(this);
                layout.addView(firstTextView);
                layout.addView(secondTextView);
                this.setContentView(layout);

                firstTextView.requestFocus();
            }
        }

        final TwoViews twoViews = new TwoViews();
        twoViews.onCreate(null);
        assertTrue(twoViews.findViewById(1).isFocused());
        assertFalse(twoViews.findViewById(2).isFocused());
    }

    @Test
    public void itDetectsAChangeInFocus() throws Exception {
        class TwoViews extends Activity {
            private TextView secondTextView;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                final TextView firstTextView = new TextView(this);
                firstTextView.setId(1);
                secondTextView = new TextView(this);
                secondTextView.setId(2);
                final LinearLayout layout = new LinearLayout(this);
                layout.addView(firstTextView);
                layout.addView(secondTextView);
                this.setContentView(layout);

                firstTextView.requestFocus();
            }

            public void changeFocus(View clicked) {
                secondTextView.requestFocus();
            }
        }

        final TwoViews twoViews = new TwoViews();
        twoViews.onCreate(null);
        twoViews.changeFocus(null);
        assertFalse(twoViews.findViewById(1).isFocused());
        assertTrue(twoViews.findViewById(2).isFocused());
    }

}
