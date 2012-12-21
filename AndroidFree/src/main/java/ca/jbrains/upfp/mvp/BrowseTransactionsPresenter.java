package ca.jbrains.upfp.mvp;

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
