package ca.jbrains.upfp.view.test;

public interface CsvFormat<ValueType> {
  String invoke(ValueType value);
}
