package ca.jbrains.upfp.controller.android.test;

import android.widget.Button;
import ca.jbrains.upfp.*;
import ca.jbrains.upfp.controller.android
    .AndroidDevicePublicStorageGateway;
import ca.jbrains.upfp.model.BrowseTransactionsModel;
import ca.jbrains.upfp.presenter.*;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.jmock.*;
import org.junit.Test;
import org.junit.runner.RunWith;

// I can't run the Activity without Robolectric,
// and I can't run with both Robolectric and JMock,
// so I choose to run with Robolectric and do the JMock
// stuff by hand.
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

    new BrowseTransactionsActivity(
        rendersView, null, null, null) {
      // Make this method visible so that I can format it
      // in the test.
      // REFACTOR Move to ShadowActivity
      @Override
      public void onResume() {
        super.onResume();
      }
    }.onResume();

    mockery.assertIsSatisfied();
  }

  @Test
  public void exportAllTransactionsButtonDoesNotBlowUp()
      throws Exception {
    final ExportAllTransactionsAction
        exportAllTransactionsAction = mockery.mock(
        ExportAllTransactionsAction.class);
    final AndroidDevicePublicStorageGateway
        androidDevicePublicStorageGateway = mockery.mock(
        AndroidDevicePublicStorageGateway.class);
    final BrowseTransactionsModel browseTransactionsModel
        = mockery.mock(BrowseTransactionsModel.class);

    final BrowseTransactionsActivity
        browseTransactionsActivity
        = new BrowseTransactionsActivity();
    browseTransactionsActivity.onCreate(null);
    browseTransactionsActivity.setCollaborators(
        exportAllTransactionsAction,
        androidDevicePublicStorageGateway,
        browseTransactionsModel);

    mockery.checking(
        new Expectations() {{
          ignoring(exportAllTransactionsAction);
          ignoring(androidDevicePublicStorageGateway);
          ignoring(browseTransactionsModel);
        }});

    final Button exportAllTransactionsButton
        = (Button) browseTransactionsActivity.findViewById(
        R.id.exportAllTransactionsButton);
    exportAllTransactionsButton.performClick();

    // don't blow up
  }
}
