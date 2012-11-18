package ca.jbrains.upfp.view.test;

import ca.jbrains.upfp.model.test.Category;

public class CategoryCsvFormat
    implements CsvFormat<Category> {
  @Override
  public String format(Category category) {
    return "Bowling Winnings";
  }
}
