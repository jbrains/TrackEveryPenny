package ca.jbrains.upfp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import ca.jbrains.upfp.controller.android.*;
import ca.jbrains.upfp.model.*;
import ca.jbrains.upfp.model.android
    .AndroidDevicePublicStorageGatewayImpl;
import ca.jbrains.upfp.presenter.*;
import ca.jbrains.upfp.view.*;
import ca.jbrains.upfp.view.android
    .AndroidBrowseTransactionsView;
import com.google.common.collect.Lists;

import java.io.*;
import java.util.List;

public class BrowseTransactionsActivity extends Activity {
  private RendersView rendersView;
  private ExportAllTransactionsAction
      exportAllTransactionsAction;
  private AndroidDevicePublicStorageGateway
      androidDevicePublicStorageGateway;
  private BrowseTransactionsModel browseTransactionsModel;
  private BrowseTransactionsView browseTransactionsView;

  public BrowseTransactionsActivity() {
    // Do all this work in onCreate()
  }

  // REFACTOR Move this constructor into the "business
  // delegate"
  public BrowseTransactionsActivity(
      RendersView rendersView,
      ExportAllTransactionsAction
          exportAllTransactionsAction,
      AndroidDevicePublicStorageGateway
          androidDevicePublicStorageGateway,
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
    // Arbitrarily, I assume that I should do my work
    // after the superclass, but I don't really know.
    // REFACTOR Delegate to businessDelegate.renderView()
    rendersView.render();
  }

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    // This seems like a more logical place to initialise
    // the View, anyway.

    // SMELL This has to happen before instantiating the
    // WriteTextToFileAction
    this.androidDevicePublicStorageGateway
        = new AndroidDevicePublicStorageGatewayImpl();

    // SMELL This needs the
    // AndroidDevicePublicStorageGateway,
    // but doesn't depend directly on it
    final File exportedTransactionsPath;
    try {
      exportedTransactionsPath = new File(
          androidDevicePublicStorageGateway
              .findPublicExternalStorageDirectory(),
          "TrackEveryPenny.csv");
    } catch (PublicStorageMediaNotAvailableException
        reported) {
      handleError(
          "Couldn't save a file to public storage; media " +
          "not available",
          "No place to which to export the transactions. " +
          "Insert an SD card or connect an external " +
          "storage device and try again.",
          reported);
      return;
    } catch (PublicStorageMediaNotWritableException
        reported) {
      final String pathNotWritableAsText = reported
          .getPathNotWritable().getAbsolutePath();
      handleError(
          String.format(
              "Path %1$s not writable",
              pathNotWritableAsText), String.format(
          "Permission denied trying to export the " +
          "transactions to file %1$s",
          pathNotWritableAsText), reported);
      return;
    }

    this.exportAllTransactionsAction
        = new ExportAllTransactionsAsCsvToFileAction(
        new TransactionsCsvFileFormat(
            new TransactionsCsvHeader(),
            new TransactionCsvFormat(
                new DateCsvFormat(),
                new CategoryCsvFormat(),
                new AmountCsvFormat())),
        new WriteTextToFileAction(
            exportedTransactionsPath));

    // SMELL I have to initialize this because I can't
    // use constructor chaining yet.
    // This has to be anything that won't throw a stupid
    // exception.
    this.browseTransactionsModel
        = new BrowseTransactionsModel() {
      @Override
      public int countTransactions() {
        return findAllTransactions().size();
      }

      @Override
      public List<Transaction> findAllTransactions() {
        return Lists.newArrayList();
      }
    };

    this.browseTransactionsView
        = new AndroidBrowseTransactionsView(
        transactionsCountView());

    // REFACTOR Delegate BrowseTransactionsView behavior
    // to a new class
    this.rendersView = new BrowseTransactionsPresenter(
        this.browseTransactionsModel,
        browseTransactionsView);
  }

  private TextView transactionsCountView() {
    return (TextView) findViewById(R.id.transactionsCount);
  }

  public void exportAllTransactions(View clicked) {
    // I'm not entirely sure where this will end up
    try {
      final List<Transaction> transactions
          = browseTransactionsModel.findAllTransactions();
      androidDevicePublicStorageGateway
          .findPublicExternalStorageDirectory();
      exportAllTransactionsAction.execute(transactions);
      notifyUser(
          "Exported all transactions to " +
          "/mnt/sdcard/TrackEveryPenny.csv");
    } catch (InternalStorageException reported) {
      handleError(
          "Couldn't read data from internal storage",
          "Something strange just happened. Try again. " +
          "You might need to reinstall the application. I" +
          " feel embarrassed and ashamed.",
          reported);
    } catch (PublicStorageMediaNotAvailableException
        reported) {
      handleError(
          "Couldn't save a file to public storage; media " +
          "not available",
          "No place to which to export the transactions. " +
          "Insert an SD card or connect an external " +
          "storage device and try again.",
          reported);
    } catch (PublicStorageMediaNotWritableException
        reported) {
      final String pathNotWritableAsText = reported
          .getPathNotWritable().getAbsolutePath();
      handleError(
          String.format(
              "Path %1$s not writable",
              pathNotWritableAsText), String.format(
          "Permission denied trying to export the " +
          "transactions to file %1$s",
          pathNotWritableAsText), reported);
    } catch (IOException unhandled) {
      throw new RuntimeException(unhandled);
    }
  }

  // REUSE Any Activity
  private void handleError(
      String internalMessage, String userVisibleMessage,
      Throwable cause
  ) {
    logError(internalMessage, cause);
    notifyUser(userVisibleMessage);
  }

  // REUSE Any Activity
  private void notifyUser(String message) {
    Toast.makeText(
        getApplicationContext(), message, Toast.LENGTH_LONG)
        .show();
  }

  // REUSE Anywhere in this app
  private void logError(
      String message, Throwable reported
  ) {
    Log.e("TrackEveryPenny", message, reported);
  }

  // SMELL Programming by accident
  // This should disappear after moving other behavior
  // into the businessDelegate.
  public void setCollaborators(
      ExportAllTransactionsAction
          exportAllTransactionsAction,
      AndroidDevicePublicStorageGateway androidDevicePublicStorageGateway,
      BrowseTransactionsModel browseTransactionsModel
  ) {
    this.exportAllTransactionsAction
        = exportAllTransactionsAction;
    this.androidDevicePublicStorageGateway
        = androidDevicePublicStorageGateway;
    this.browseTransactionsModel = browseTransactionsModel;
  }

}
