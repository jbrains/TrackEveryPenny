package ca.jbrains.upfp.model.test;

public final class Category {
  private final String name;

  public Category(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof Category) {
      final Category that = (Category) other;
      return this.name.equals(that.name);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public String toString() {
    return name;
  }
}
