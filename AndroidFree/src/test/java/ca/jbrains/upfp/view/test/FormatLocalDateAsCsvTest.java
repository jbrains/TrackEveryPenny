package ca.jbrains.upfp.view.test;

import org.joda.time.LocalDate;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FormatLocalDateAsCsvTest {
  @Test
  public void happyPath() throws Exception {
    assertEquals(
        "1974-05-04", new DateCsvFormat().format(
        new LocalDate(1974, 5, 4)));
  }
}
