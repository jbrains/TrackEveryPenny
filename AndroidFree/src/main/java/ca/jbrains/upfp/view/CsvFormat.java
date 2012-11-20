package ca.jbrains.upfp.view;

public interface CsvFormat<T> {
  String format(T t);
}
