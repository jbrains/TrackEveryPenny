package ca.jbrains.upfp.view.test;

import ca.jbrains.upfp.view.WriteTextToFileActionImpl;
import org.apache.commons.io.FileUtils;
import org.junit.*;

import java.io.File;

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

    new WriteTextToFileActionImpl().writeTextToFile(
        "::text::", file);

    assertEquals(
        "::text::", FileUtils.readFileToString(
        file));
  }
}
