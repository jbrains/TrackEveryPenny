package ca.jbrains.upfp.view.test;

public interface CsvFormat<ValueType> {
  String format(ValueType value);
}
