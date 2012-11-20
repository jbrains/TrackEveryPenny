package ca.jbrains.upfp.view;

import ca.jbrains.upfp.model.Category;
import ca.jbrains.upfp.view.CsvFormat;

public class CategoryCsvFormat
    implements CsvFormat<Category> {
  @Override
  public String format(Category category) {
    return category.getName();
  }
}
