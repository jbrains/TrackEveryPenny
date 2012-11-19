package ca.jbrains.upfp.view;

import ca.jbrains.upfp.presenter.test.WriteTextAction;

import java.io.*;

public class WriteTextToFileActionImpl
    implements WriteTextAction {
  private final File destinationFile;

  public WriteTextToFileActionImpl(File destinationFile) {
    this.destinationFile = destinationFile;
  }

  protected FileWriter fileWriterOnDestinationFile()
      throws IOException {
    return new FileWriter(destinationFile);
  }

  @Override
  public void writeText(String text) throws IOException {
    final FileWriter fileWriter
        = fileWriterOnDestinationFile();
    fileWriter.write(text);
    fileWriter.flush();
    fileWriter.close();
  }
}
