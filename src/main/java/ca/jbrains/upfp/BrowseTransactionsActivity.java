package ca.jbrains.upfp;

import android.app.Activity;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.*;
import ca.jbrains.toolkit.ProgrammerMistake;
import ca.jbrains.upfp.domain.*;
import ca.jbrains.upfp.mvp.*;
import com.google.common.collect.Lists;
import org.joda.time.LocalDate;
import org.joda.time.format.*;

import java.io.*;
import java.text.*;
import java.util.ArrayList;

public class BrowseTransactionsActivity extends Activity
    implements BrowseTransactionsView {
  public static final DateTimeFormatter DATE_TIME_FORMATTER
      = DateTimeFormat.forPattern("yyyy-MM-dd");
  // REFACTOR Move down into Android-free model layer
  private final ArrayList<Transaction> transactions = Lists
      .newArrayList(
          new Transaction(
              new LocalDate(2012, 11, 5),
              "Bowling Winnings",
              200),
          new Transaction(
              new LocalDate(2012, 11, 5), "Bowling", -1400),
          new Transaction(
              new LocalDate(2012, 11, 7), "Bowling", -1050),
          new Transaction(
              new LocalDate(2012, 11, 7),
              "Bowling Winnings", 100),
          new Transaction(
              new LocalDate(2012, 11, 8),
              "Groceries", -2500),
          new Transaction(
              new LocalDate(2012, 11, 10),
              "Groceries", -11715),
          new Transaction(
              new LocalDate(2012, 11, 12),
              "Bowling", -1400),
          new Transaction(
              new LocalDate(2012, 11, 17),
              "Groceries", -1350)
      );
  private final RendersView rendersView;

  public BrowseTransactionsActivity() {
    // We can't chain the constructor, because the instance in the process of being created is itself the view.
    // We have to wait for super() to be (implicitly) invoked.
    this.rendersView = new BrowseTransactionsPresenter(
        new BrowseTransactionsModel() {
          @Override
          public int countTransactions() {
            return 12;
          }
        }, this);
  }

  public BrowseTransactionsActivity(
      RendersView rendersView
  ) {
    this.rendersView = rendersView;
  }

  @Override
  protected void onResume() {
    super.onResume();
    // Arbitrarily, I assume that I should do my work after the superclass, but I don't really know.
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

  public void exportTransactionsToCsv(View clicked) {
    final String externalStorageState = Environment
        .getExternalStorageState();
    if (!Environment.MEDIA_MOUNTED.equals(
        externalStorageState)) {
      notifyUser(
          "There's no external storage available, so I can't export.");
      return;
    }

    final File externalStorageDirectory = Environment
        .getExternalStorageDirectory();
    final File externalDownloadsDirectory = new File(
        externalStorageDirectory,
        Environment.DIRECTORY_DOWNLOADS);
    exportTransactionsToCsvFileInDirectory(
        externalDownloadsDirectory);
  }

  // REUSE Android-free
  // REFACTOR Controller implementation
  private void exportTransactionsToCsvFileInDirectory(
      File externalDownloadsDirectory
  ) {
    final File transactionsCsvFile = new File(
        externalDownloadsDirectory, "TrackEveryPenny.csv");
    try {
      final PrintWriter canvas = new PrintWriter(
          transactionsCsvFile);
      writeCsvContentsTo(canvas, transactions);
      canvas.flush();
      canvas.close();
      notifyUser(
          "Exported transactions to " +
          transactionsCsvFile.getAbsolutePath());
    } catch (IOException logged) {
      notifyUser(
          "I tried to write to external storage, but failed.");
      logError(
          "Failed to write to external public storage",
          logged);
    }
  }

  // REUSE Domain
  private void writeCsvContentsTo(
      PrintWriter canvas, Iterable<Transaction> transactions
  ) {
    // SMELL Feels wrong somehow
    // REFACTOR RowFormat goes with HeaderFormat to make CsvFileFormat,
    // then export with TransactionCsvFileFormat?
    new ExportTransactionsToCsvAction(
        new TransactionCsvRowFormat()).execute(
        transactions,
        canvas);
  }

  // REUSE App-wide
  private void logError(
      String errorText, Exception logged
  ) {
    Log.e("TrackEveryPenny", errorText, logged);
  }

  // REUSE App-wide
  public void notifyUser(String message) {
    Toast.makeText(
        getApplicationContext(), message, Toast.LENGTH_LONG)
        .show();
  }

  public void addTransactionOnCurrentDate(View clicked) {
    // REFACTOR Move all semantic validation here?

    try {
      final LocalDate date = lookupDate();
      final int amountInCents = lookupAmount();
      final CashDirection cashDirection
          = lookupCashDirection();
      final String categoryName = lookupCategoryName();

      // model.addTransactionOnCurrentDate(date, amountInCents,
      // cashDirection, categoryName)

      if (categoryName.isEmpty()) {
        notifyUser("Category name can't be blank.");
        //categoryView().requestFocus();
        return;
      }

      final double amountInDollars = amountInCents / 100.0d;

      notifyUser(
          String.format(
              "Adding a transaction on %1$s for %2$.2f" +
              " %3$s in %4$s", formatDate(date),
              amountInDollars,
              formatCashDirection(cashDirection),
              categoryName));
    } catch (ProgrammerMistake logged) {
      // SMELL This will be duplicated in every event handler
      Log.e(
          "TrackEveryPenny", "A programmer made a mistake",
          logged);
      notifyUser(
          "Wow, this is embarrassing. I'm really sorry. " +
          "Something went wrong, and I couldn't add your " +
          "transaction. Try again?");
    } catch (ParseException wrapped) {
      throw new ProgrammerMistake(
          "Unhandled exception; lazy " +
          "programmer!", wrapped);
    }
  }

  private String formatCashDirection(
      CashDirection cashDirection
  ) {
    return (cashDirection == CashDirection.IN) ? "IN"
                                               : "OUT";
  }

  private String formatDate(LocalDate date) {
    return "2011-11-14";
  }

  public String lookupCategoryName() {
    return categoryView().getText().toString();
  }

  private CashDirection lookupCashDirection() {
    final ToggleButton cashDirectionView
        = (ToggleButton) findViewById(
        R
            .id.cashDirection);
    final CashDirection cashDirection =
        cashDirectionView.isChecked() ?
        CashDirection.IN : CashDirection.OUT;
    return cashDirection;
  }

  private int lookupAmount() throws ParseException {
    return lookupAmountInCents();
  }

  public LocalDate lookupDate() {
    try {
      return DATE_TIME_FORMATTER.parseLocalDate(
          dateView().getText().toString());
    } catch (IllegalArgumentException invalidDateText) {
      throw new ProgrammerMistake(
          "The user somehow edited the date, " +
          "and entered something that isn't a date.",
          invalidDateText);
    }
  }

  public void onCashDirectionToggled(View clicked) {
    // Nothing needed?
  }

  public TextView dateView() {
    return (TextView) findViewById(R.id.date);
  }

  public int lookupAmountInCents() throws ParseException {
    final float amountInDollars = lookupAmountInDollars();
    final int amountInCents = Math.round(
        100.0f * amountInDollars);
    amountView().setText(
        String.valueOf(
            amountInCents / 100.0d));
    return amountInCents;
  }

  private float lookupAmountInDollars()
      throws ParseException {
    return new DecimalFormat("#.##").parse(
        amountView().getText().toString()).floatValue();
  }

  public TextView amountView() {
    return (TextView) findViewById(R.id.amount);
  }

  public TextView categoryView() {
    return (TextView) findViewById(R.id.category);
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
}
