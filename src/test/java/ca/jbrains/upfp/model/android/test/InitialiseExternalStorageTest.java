package ca.jbrains.upfp.model.android.test;

import android.os.Environment;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class InitialiseExternalStorageTest {
  @Test
  public void happyPath() throws Exception {
    final File externalStoragePublicDirectory = new File(
        "/irrelevant/path");

    assertEquals(
        externalStoragePublicDirectory,
        new AndroidDevicePublicStorageGatewayImpl() {
          @Override
          public String getExternalStorageState() {
            return Environment.MEDIA_MOUNTED;
          }

          @Override
          public File getExternalStoragePublicDirectory() {
            return externalStoragePublicDirectory;
          }
        }.findPublicExternalStorageDirectory());
  }
}
