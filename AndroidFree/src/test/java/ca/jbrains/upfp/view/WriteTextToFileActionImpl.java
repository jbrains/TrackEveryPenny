package ca.jbrains.upfp.view;

import ca.jbrains.upfp.presenter.test.WriteTextToFileAction;

import java.io.*;

public class WriteTextToFileActionImpl
    implements WriteTextToFileAction {
  @Override
  public void writeTextToFile(String text, File path)
      throws IOException {
    final FileWriter fileWriter = fileWriterOn(path);
    fileWriter.write(text);
    fileWriter.flush();
    fileWriter.close();
  }

  protected FileWriter fileWriterOn(File path)
      throws IOException {
    return new FileWriter(path);
  }
}
