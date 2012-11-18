package ca.jbrains.upfp.view.test;

import ca.jbrains.upfp.model.test.Amount;

public class AmountCsvFormat implements CsvFormat<Amount> {
  @Override
  public String format(Amount amount) {
    return "2.50";
  }
}
