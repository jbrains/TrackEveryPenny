package ca.jbrains.upfp.model.test;

public final class Amount {
  private final int cents;

  public Amount(int cents) {
    this.cents = cents;
  }

  public static Amount cents(int cents) {
    return new Amount(cents);
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof Amount) {
      final Amount that = (Amount) other;
      return this.cents == that.cents;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return cents;
  }

  @Override
  public String toString() {
    return String.format("%1d cents", cents);
  }
}
