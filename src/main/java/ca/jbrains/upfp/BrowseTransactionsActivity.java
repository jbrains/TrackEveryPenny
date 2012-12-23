package ca.jbrains.upfp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import ca.jbrains.toolkit.ProgrammerMistake;
import ca.jbrains.upfp.controller.ExportAllTransactionsAction;
import ca.jbrains.upfp.mvp.*;
import com.google.common.collect.Lists;

import java.util.Collection;

public class BrowseTransactionsActivity extends Activity
    implements BrowseTransactionsView {
  private final RendersView rendersView;

  public BrowseTransactionsActivity() {
    // We can't chain the constructor, because the instance
    // in the process of being created is itself the view.
    // We have to wait for super() to be (implicitly) invoked.
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
  }

  /**
   * @deprecated
   */
  public BrowseTransactionsActivity(
      RendersView rendersView
  ) {
    this(rendersView, null);
  }

  public BrowseTransactionsActivity(
      RendersView rendersView, ExportAllTransactionsAction
      exportAllTransactionsAction
  ) {

    this.rendersView = rendersView;
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
    Toast.makeText(
        getApplicationContext(),
        "Exported all transactions to /mnt/sdcard/TrackEveryPenny.csv",
        Toast.LENGTH_LONG).show();
  }
}
