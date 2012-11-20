package ca.jbrains.upfp.model.android.test;

import android.os.Environment;
import ca.jbrains.upfp.controller.android.*;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

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

  @Test
  public void mediaMountedInReadOnlyMode()
      throws Exception {
    final File externalStoragePublicDirectory = new File(
        "/irrelevant/path");

    try {
      new AndroidDevicePublicStorageGatewayImpl() {
        @Override
        public String getExternalStorageState() {
          return Environment.MEDIA_MOUNTED_READ_ONLY;
        }

        @Override
        public File getExternalStoragePublicDirectory() {
          return externalStoragePublicDirectory;
        }
      }.findPublicExternalStorageDirectory();
      fail(
          "Why did you give the client a directory that " +
          "they can't write to?!");
    } catch (PublicStorageMediaNotWritableException
        success) {
      assertEquals(
          externalStoragePublicDirectory,
          success.getPathNotWritable());
    }
  }

  @Test
  public void mediaNotAvailable() throws Exception {
    try {
      new AndroidDevicePublicStorageGatewayImpl() {
        @Override
        public String getExternalStorageState() {
          // Anything except (MEDIA_MOUNTED or
          // MEDIA_MOUNTED_READ_ONLY)
          return Environment.MEDIA_UNMOUNTABLE;
        }
      }.findPublicExternalStorageDirectory();
      fail(
          "Why did you give the client a directory that " +
          "they can't write to?!");
    } catch (PublicStorageMediaNotAvailableException success) {
    }
  }
}
