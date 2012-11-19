package ca.jbrains.upfp.view.test;

import com.google.common.base.Joiner;
import com.google.common.collect.*;

import java.util.ArrayList;

// This has to match the implementation of
// CsvFormat<Transaction>
public class TransactionsCsvHeader
    implements CsvHeaderFormat {
  public static final ArrayList<String> COLUMN_NAMES = Lists
      .newArrayList("Date", "Category", "Amount");

  @Override
  public String formatHeader() {
    return Joiner.on(",").join(
        Collections2.transform(
            COLUMN_NAMES, new SurroundWithQuotes()));
  }
}