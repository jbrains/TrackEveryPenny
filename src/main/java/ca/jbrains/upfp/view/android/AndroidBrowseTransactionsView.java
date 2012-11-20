package ca.jbrains.upfp.view.android;

import android.widget.TextView;
import ca.jbrains.toolkit.ProgrammerMistake;
import ca.jbrains.upfp.view.BrowseTransactionsView;

public class AndroidBrowseTransactionsView
    implements BrowseTransactionsView {
  private final TextView transactionsCountView;

  public AndroidBrowseTransactionsView(
      TextView transactionsCountView
  ) {
    this.transactionsCountView = transactionsCountView;
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

    transactionsCountView.setText(
        String.format(
            "%1$d", numberOfTransactions));
  }
}
