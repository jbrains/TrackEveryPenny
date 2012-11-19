package ca.jbrains.upfp.view.test;

import ca.jbrains.upfp.model.Category;

public class CategoryCsvFormat
    implements CsvFormat<Category> {
  @Override
  public String format(Category category) {
    return category.getName();
  }
}
