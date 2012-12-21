package ca.jbrains.upfp.controller.android.test;

import ca.jbrains.upfp.BrowseTransactionsActivity;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.jmock.*;
import org.junit.Test;
import org.junit.runner.RunWith;

// I can't run the Activity without Robolectric, and I
// can't run with both Robolectric and JMock, so I choose
// to run with Robolectric and do the JMock stuff by hand.
@RunWith(RobolectricTestRunner.class)
public class RenderBrowseTransactionsScreenTest {
  private Mockery mockery = new Mockery();

  @Test
  public void askToRenderOnResume() throws Exception {
    final RendersView rendersView = mockery.mock(
        RendersView.class);

    mockery.checking(
        new Expectations() {{
          oneOf(rendersView).render();
        }});

    new BrowseTransactionsActivity(rendersView) {
      // Make this method visible so that I can invoke it
      // in the test.
      //
      // REFACTOR Move to ShadowActivity
      @Override
      public void onResume() {
        super.onResume();
      }
    }.onResume();

    mockery.assertIsSatisfied();
  }
}
