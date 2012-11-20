package ca.jbrains.upfp.view.android;

import android.widget.TextView;
import ca.jbrains.toolkit.ProgrammerMistake;
import ca.jbrains.upfp.*;
import ca.jbrains.upfp.view.BrowseTransactionsView;

public class AndroidBrowseTransactionsView
    implements BrowseTransactionsView {
  private BrowseTransactionsActivity
      browseTransactionsActivity;

  public AndroidBrowseTransactionsView(
      BrowseTransactionsActivity browseTransactionsActivity
  ) {
    this.browseTransactionsActivity
        = browseTransactionsActivity;
  }

  @Override
  public void displayNumberOfTransactions(
      int numberOfTransactions
  ) {
    if (numberOfTransactions < 0)
      throw new ProgrammerMistake(
          new IllegalArgumentException(
              String.format(
                  "number of transactions can't be " +
                  "negative, but it's %1$d",
                  numberOfTransactions)));

    final TextView transactionsCountView
        = (TextView) browseTransactionsActivity
        .findViewById(
            R.id.transactionsCount);
    transactionsCountView.setText(
        String.format(
            "%1$d", numberOfTransactions));
  }
}
