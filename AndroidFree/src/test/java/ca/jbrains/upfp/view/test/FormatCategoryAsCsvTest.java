package ca.jbrains.upfp.view.test;

import ca.jbrains.upfp.model.Category;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FormatCategoryAsCsvTest {
  @Test
  public void happyPath() throws Exception {
    final Category category = new Category(
        "A valid category name");
    final CategoryCsvFormat categoryCsvFormat
        = new CategoryCsvFormat();
    assertEquals(
        "A valid category name", categoryCsvFormat.format(
        category));
  }
}
