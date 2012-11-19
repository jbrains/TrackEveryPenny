package ca.jbrains.upfp.presenter.test;

import java.io.*;

public interface WriteTextToFileAction {
  void writeTextToFile(String csvText, File path)
      throws IOException;
}
