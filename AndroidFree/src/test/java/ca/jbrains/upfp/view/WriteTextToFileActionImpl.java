package ca.jbrains.upfp.view;

import ca.jbrains.upfp.presenter.test.WriteTextAction;

import java.io.*;

public class WriteTextToFileActionImpl
    implements WriteTextAction {
  private final File file;

  public WriteTextToFileActionImpl(File file) {
    this.file = file;
  }

  protected FileWriter fileWriterOn(File path)
      throws IOException {
    return new FileWriter(path);
  }

  @Override
  public void writeText(String text) throws IOException {
    final FileWriter fileWriter = fileWriterOn(file);
    fileWriter.write(text);
    fileWriter.flush();
    fileWriter.close();
  }
}
