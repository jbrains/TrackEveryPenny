package ca.jbrains.upfp.view;

import ca.jbrains.upfp.presenter.WriteTextAction;

import java.io.*;

public class WriteTextToFileAction
    implements WriteTextAction {
  private final File destinationFile;

  public WriteTextToFileAction(File destinationFile) {
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
