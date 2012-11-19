package ca.jbrains.upfp.presenter.test;

import java.io.*;

public interface WriteTextToFileAction {
  void writeTextToFile(String text, File path)
      throws IOException;
}
