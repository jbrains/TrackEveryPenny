package ca.jbrains.upfp.view.test;

import org.joda.time.LocalDate;

public class DateCsvFormat implements CsvFormat<LocalDate> {
  @Override
  public String format(LocalDate date) {
    return "2012-11-14";
  }
}
