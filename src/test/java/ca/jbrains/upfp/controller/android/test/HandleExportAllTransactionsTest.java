package ca.jbrains.upfp.controller.android.test;

import ca.jbrains.upfp.*;
import ca.jbrains.upfp.controller.ExportAllTransactionsAction;
import ca.jbrains.upfp.mvp.BrowseTransactionsModel;
import com.google.common.collect.Lists;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.*;
import org.jmock.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;
import java.util.regex.Pattern;

import static ca.jbrains.hamcrest.RegexMatcher.matches;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class HandleExportAllTransactionsTest {
  private Mockery mockery = new Mockery();

  @Test
  public void happyPath() throws Exception {
    final BrowseTransactionsModel browseTransactionsModel
        = mockery.mock(BrowseTransactionsModel.class);
    final ExportAllTransactionsAction
        exportAllTransactionsAction = mockery.mock(
        ExportAllTransactionsAction.class);

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

          allowing(exportAllTransactionsAction).execute();
          // succeeds by not throwing an exception
        }});

    final BrowseTransactionsActivity
        browseTransactionsActivity
        = new BrowseTransactionsActivity
        (null, exportAllTransactionsAction);
    browseTransactionsActivity.onCreate(null);

    browseTransactionsActivity.exportAllTransactions(
        browseTransactionsActivity.findViewById(
            R.id.exportAllTransactionsButton));

    ShadowHandler.idleMainLooper();
    assertThat(
        ShadowToast.getTextOfLatestToast(),
        matches(
            Pattern.compile(
                "Exported all transactions to (.+)\\.csv")));
  }
}
