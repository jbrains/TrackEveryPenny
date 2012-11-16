package ca.jbrains.upfp.model.test;

import org.junit.Test;

import static org.junit.Assert.fail;

public class CreateCategoryTest {
  @Test
  public void requireKeyInformation() throws Exception {
    try {
      new Category(null);
      fail("Why did you create a Category with no name?!");
    } catch (IllegalArgumentException success) {
    }
  }
}
