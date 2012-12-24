package ca.jbrains.upfp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import ca.jbrains.toolkit.ProgrammerMistake;
import ca.jbrains.upfp.controller.ExportAllTransactionsAction;
import ca.jbrains.upfp.controller.android.*;
import ca.jbrains.upfp.mvp.*;
import com.google.common.collect.Lists;

import java.io.File;
import java.util.Collection;

public class BrowseTransactionsActivity extends Activity
    implements BrowseTransactionsView {
  private final RendersView rendersView;
  private final ExportAllTransactionsAction
      exportAllTransactionsAction;
  private final AndroidDevicePublicStorageGateway
      androidDevicePublicStorageGateway;
  private final BrowseTransactionsModel
      browseTransactionsModel;

  public BrowseTransactionsActivity() {
    // We can't chain the constructor, because the instance
    // in the process of being created is itself the view.
    // We have to wait for super() to be (implicitly) invoked.

    // REFACTOR Delegate BrowseTransactionsView behavior to a new class
    this.rendersView = new BrowseTransactionsPresenter(
        new BrowseTransactionsModel() {
          @Override
          public int countTransactions() {
            return 12;
          }

          @Override
          public Collection<Object> findAllTransactions() {
            return Lists.newArrayList();
          }
        }, this);

    this.exportAllTransactionsAction
        = new ExportAllTransactionsAction() {
      @Override
      public void execute() {
        // Do nothing, for now
      }
    };

    // SMELL I have to initialize this because I can't use
    // constructor chaining yet. This has to be anything
    // that won't throw a stupid exception.
    this.browseTransactionsModel
        = new BrowseTransactionsModel() {
      @Override
      public int countTransactions() {
        return 0;
      }

      @Override
      public Collection<Object> findAllTransactions() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
      }
    };

    this.androidDevicePublicStorageGateway
        = new AndroidDevicePublicStorageGateway() {
      @Override
      public File findPublicExternalStorageDirectory()
          throws PublicStorageMediaNotAvailableException {
        return new File(".");
      }
    };
  }

  /**
   * @deprecated
   */
  public BrowseTransactionsActivity(
      RendersView rendersView
  ) {
    this(rendersView, null, null, null);
  }

  public BrowseTransactionsActivity(
      RendersView rendersView,
      ExportAllTransactionsAction exportAllTransactionsAction,
      AndroidDevicePublicStorageGateway androidDevicePublicStorageGateway,
      BrowseTransactionsModel browseTransactionsModel
  ) {

    this.rendersView = rendersView;
    this.exportAllTransactionsAction
        = exportAllTransactionsAction;
    this.androidDevicePublicStorageGateway
        = androidDevicePublicStorageGateway;
    this.browseTransactionsModel = browseTransactionsModel;
  }

  @Override
  protected void onResume() {
    super.onResume();
    // Arbitrarily, I assume that I should do my work after
    // the superclass, but I don't really know.
    rendersView.render();
  }

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
  }

  public void displayNumberOfTransactions(
      int transactionCount
  ) {
    if (transactionCount < 0)
      throw new ProgrammerMistake(
          new IllegalArgumentException(
              String.format(
                  "number of transactions can't be negative, but it's %1$d",
                  transactionCount)));

    final TextView transactionsCountView
        = (TextView) findViewById(R.id.transactionsCount);

    transactionsCountView.setText(
        String.format(
            "%1$d", transactionCount));
  }

  public void exportAllTransactions(View clicked) {
    try {
      browseTransactionsModel.findAllTransactions();
      androidDevicePublicStorageGateway
          .findPublicExternalStorageDirectory();
      exportAllTransactionsAction.execute();
      Toast.makeText(
          getApplicationContext(),
          "Exported all transactions to /mnt/sdcard/TrackEveryPenny.csv",
          Toast.LENGTH_LONG).show();
    } catch (InternalStorageException reported) {
      Log.wtf("TrackEveryPenny", reported);
      Toast.makeText(
          getApplicationContext(),
          "Something strange just happened. Try again. You might need to " +
          "reinstall the application. I feel embarrassed and ashamed.",
          Toast.LENGTH_LONG).show();
    } catch (PublicStorageMediaNotAvailableException reported) {
      Log.e(
          "TrackEveryPenny",
          "Couldn't save a file to public storage; media not available",
          reported);
      Toast.makeText(
          getApplicationContext(),
          "No place to which to export the transactions. Insert an SD card or connect an " +
          "external storage device and try again.",
          Toast.LENGTH_LONG).show();
    } catch (PublicStorageMediaNotWritableException reported) {
      final String pathNotWritableAsText = reported
          .getPathNotWritable().getAbsolutePath();
      Log.e(
          "TrackEveryPenny", String.format(
          "Path %1$s not writable", pathNotWritableAsText),
          reported);
      Toast.makeText(
          getApplicationContext(),
          String.format(
              "Permission denied trying to export the transactions to file %1$s",
              pathNotWritableAsText), Toast.LENGTH_LONG
      ).show();
    }
  }
}
