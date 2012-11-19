package ca.jbrains.upfp.view.test;

import ca.jbrains.upfp.view.WriteTextToFileActionImpl;
import org.apache.commons.io.FileUtils;
import org.junit.*;

import java.io.*;

import static org.junit.Assert.*;

public class WriteTextToFileTest {
  private static final File testOutputDirectory = new File(
      "./test/output/WriteTextToFileTest/");

  @BeforeClass
  public static void initialiseTestOutputAreaOnFileSystem()
      throws Exception {
    FileUtils.deleteDirectory(testOutputDirectory);
    assertTrue(
        String.format(
            "Couldn't create test output directory at %1$s",
            testOutputDirectory.getAbsolutePath()),
        testOutputDirectory.mkdirs());
  }

  @Test
  public void happyPath() throws Exception {
    final File file = new File(
        testOutputDirectory, "happyPath.csv");

    new WriteTextToFileActionImpl(file).writeText(
        "::text::");

    assertEquals(
        "::text::", FileUtils.readFileToString(
        file));
  }

  @Test
  public void ioFailure() throws Exception {
    final IOException ioFailure = new IOException(
        "Simulating a failure writing to the file.");
    final File file = new File(
        testOutputDirectory, "anyWritableFile.txt");
    try {
      new WriteTextToFileActionImpl(file) {
        @Override
        protected FileWriter fileWriterOnDestinationFile()
            throws IOException {
          return new FileWriter(file) {
            @Override
            public void write(String str, int off, int len)
                throws IOException {
              throw ioFailure;
            }
          };
        }
      }.writeText("::text::");
      fail("How did you survive the I/O failure?!");
    } catch (IOException success) {
      if (success != ioFailure) throw success;
    }
  }

  @Test
  public void fileAlreadyExists() throws Exception {
    final File file = new File(
        testOutputDirectory, "alreadyExists.txt");
    FileUtils.write(
        file, "There is already something here.");

    new WriteTextToFileActionImpl(file).writeText(
        "::text::");

    assertEquals(
        "::text::", FileUtils.readFileToString(
        file));
  }
}
