package ca.jbrains.upfp.model.test;

public class Amount {
  public Amount(int cents) {
  }

  static Amount cents(int cents) {
    return new Amount(cents);
  }
}
