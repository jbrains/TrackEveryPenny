package ca.jbrains.upfp.view.test;

import ca.jbrains.upfp.model.Amount;

public class AmountCsvFormat implements CsvFormat<Amount> {
  @Override
  public String format(Amount amount) {
    return String.format("%1$.2f", amount.inDollars());
  }
}
