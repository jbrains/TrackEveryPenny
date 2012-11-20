package ca.jbrains.upfp.view;

import ca.jbrains.upfp.model.Amount;
import ca.jbrains.upfp.view.CsvFormat;

public class AmountCsvFormat implements CsvFormat<Amount> {
  @Override
  public String format(Amount amount) {
    return String.format("%1$.2f", amount.inDollars());
  }
}
