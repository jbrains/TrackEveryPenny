package ca.jbrains.upfp.controller.android.test;

import ca.jbrains.upfp.*;
import ca.jbrains.upfp.controller.ExportAllTransactionsAction;
import ca.jbrains.upfp.controller.android.*;
import ca.jbrains.upfp.mvp.BrowseTransactionsModel;
import com.google.common.collect.Lists;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.*;
import org.jmock.*;
import org.junit.*;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.Collection;
import java.util.regex.Pattern;

import static ca.jbrains.hamcrest.RegexMatcher.matches;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class HandleExportAllTransactionsTest {
  private Mockery mockery = new Mockery();
  private final BrowseTransactionsModel
      browseTransactionsModel = mockery.mock(
      BrowseTransactionsModel.class);
  private final ExportAllTransactionsAction
      exportAllTransactionsAction = mockery.mock(
      ExportAllTransactionsAction.class);
  private final AndroidDevicePublicStorageGateway
      androidDevicePublicStorageGateway = mockery.mock(
      AndroidDevicePublicStorageGateway.class);

  private final BrowseTransactionsActivity
      browseTransactionsActivity
      = new BrowseTransactionsActivity(
      null, exportAllTransactionsAction,
      androidDevicePublicStorageGateway
  );

  @Before
  public void initializeActivity() {
    browseTransactionsActivity.onCreate(null);
  }

  @Test
  public void happyPath() throws Exception {
    final Collection<Object>
        anyValidNonTrivialCollectionOfTransactions = Lists
        .newArrayList(
            new Object(), new Object(),
            new Object());

    mockery.checking(
        new Expectations() {{
          allowing(browseTransactionsModel)
              .findAllTransactions();
          will(
              returnValue(
                  anyValidNonTrivialCollectionOfTransactions));

          // SMELL Irrelevant detail
          ignoring(androidDevicePublicStorageGateway)
              .findPublicExternalStorageDirectory();

          allowing(exportAllTransactionsAction).execute();
          // succeeds by not throwing an exception
        }});

    pressExportAllTransactionsButton(
        browseTransactionsActivity);

    assertLastToastMatchesRegex(
        "Exported all transactions to (.+)\\.csv");
  }

  @Test
  public void mediaNotAvailable() throws Exception {
    mockery.checking(
        new Expectations() {{
          allowing(browseTransactionsModel)
              .findAllTransactions();

          allowing(androidDevicePublicStorageGateway)
              .findPublicExternalStorageDirectory();
          will(
              throwException(
                  new PublicStorageMediaNotAvailableException()));

          never(exportAllTransactionsAction);
        }});

    pressExportAllTransactionsButton(
        browseTransactionsActivity);

    assertLastToastMatchesRegex(
        "No place to which to export the transactions. " +
        "Insert an SD card or connect an " +
        "external storage device and try again.");
  }

  @Test
  public void mediaNotWritable() throws Exception {
    mockery.checking(
        new Expectations() {{
          allowing(browseTransactionsModel)
              .findAllTransactions();

          allowing(androidDevicePublicStorageGateway)
              .findPublicExternalStorageDirectory();
          will(
              throwException(
                  new PublicStorageMediaNotWritableException(
                      new File(
                          "/mnt/sdcard/TrackEveryPenny.csv"))));

          never(exportAllTransactionsAction);
        }});

    pressExportAllTransactionsButton(
        browseTransactionsActivity);

    assertLastToastMatchesRegex(
        "Permission denied trying to export the transactions to file " +
        "/mnt/sdcard/TrackEveryPenny.csv");
  }

  private void pressExportAllTransactionsButton(
      BrowseTransactionsActivity browseTransactionsActivity
  ) {
    browseTransactionsActivity.exportAllTransactions(
        browseTransactionsActivity.findViewById(
            R.id.exportAllTransactionsButton));
  }

  public static void assertLastToastMatchesRegex(
      String patternText
  ) {
    ShadowHandler.idleMainLooper();
    assertThat(
        ShadowToast.getTextOfLatestToast(),
        matches(Pattern.compile(patternText)));
  }
}
