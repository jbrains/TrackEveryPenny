package ca.jbrains.upfp.presenter;

import ca.jbrains.upfp.model.BrowseTransactionsModel;
import ca.jbrains.upfp.view.BrowseTransactionsView;

public class BrowseTransactionsPresenter
    implements RendersView {
  private final BrowseTransactionsModel model;
  private final BrowseTransactionsView view;

  public BrowseTransactionsPresenter(
      BrowseTransactionsModel model,
      BrowseTransactionsView view
  ) {
    this.model = model;
    this.view = view;
  }

  public void render() {
    view.displayNumberOfTransactions(
        model.countTransactions());
  }
}
