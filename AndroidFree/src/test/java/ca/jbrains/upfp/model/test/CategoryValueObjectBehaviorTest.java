package ca.jbrains.upfp.model.test;

import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryValueObjectBehaviorTest {
  @Test
  public void reflexive() throws Exception {
    final Category category = new Category(
        "any valid name");
    assertEquals(category, category);
  }

  @Test
  public void symmetric() throws Exception {
    final Category equalCategory1 = new Category(
        "the same name");
    final Category equalCategory2 = new Category(
        "the same name");
    final Category unequalCategory = new Category(
        "any other name");

    // both must be true; equivalent to iff
    assertEquals(equalCategory1, equalCategory2);
    assertEquals(equalCategory2, equalCategory1);

    // both must be true; equivalent to iff
    assertFalse(equalCategory1.equals(unequalCategory));
    assertFalse(unequalCategory.equals(equalCategory1));

    // if we get here, then we know equalCategory2 does not
    // equal unequalCategory
  }

  @Test
  public void transitive() throws Exception {
    final Category equalCategory1 = new Category(
        "the same name");
    final Category equalCategory2 = new Category(
        "the same name");
    final Category equalCategory3 = new Category(
        "the same name");
    final Category unequalCategory = new Category(
        "any other name");

    // iff again
    assertEquals(equalCategory1, equalCategory2);
    assertEquals(equalCategory2, equalCategory3);
    assertEquals(equalCategory1, equalCategory3);

    // un != ec1 && ec1 == ec2 (as above) => un != ec2
    assertFalse(unequalCategory.equals(equalCategory1));
    assertFalse(unequalCategory.equals(equalCategory2));

    // symmetry of && means + the above means
    // ec1 == ec2 && ec2 != un => ec1 != un
    // no need to test
  }

  @Test
  public void nothingEqualsNull() throws Exception {
    assertFalse(
        new Category("irrelevant name").equals(
            null));
  }

  // I'm willing to forgo testing for consistency, as long
  // as Category remains immutable.
}
