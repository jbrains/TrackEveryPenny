package ca.jbrains.upfp.view.test;

import com.google.common.base.Function;
import com.sun.istack.internal.Nullable;

class SurroundWithQuotes
    implements Function<String, String> {
  public static final SurroundWithQuotes INSTANCE
      = new SurroundWithQuotes();

  @Override
  public String apply(@Nullable String text) {
    return "\"" + text + "\"";
  }
}
