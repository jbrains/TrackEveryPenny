package ca.jbrains.upfp.model.android.test;

import android.os.Environment;
import ca.jbrains.upfp.controller.android.*;

import java.io.File;

public class AndroidDevicePublicStorageGatewayImpl
    implements AndroidDevicePublicStorageGateway {
  public String getExternalStorageState() {
    return Environment.getExternalStorageState();
  }

  public File getExternalStoragePublicDirectory() {
    return Environment.getExternalStoragePublicDirectory(
        Environment.DIRECTORY_DOWNLOADS);
  }

  @Override
  public File findPublicExternalStorageDirectory()
      throws PublicStorageMediaNotAvailableException,
             PublicStorageMediaNotWritableException {
    if (Environment.MEDIA_MOUNTED.equals(
        getExternalStorageState()))
      return getExternalStoragePublicDirectory();

    if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(
        getExternalStorageState()))
      throw new PublicStorageMediaNotWritableException(
          getExternalStoragePublicDirectory());

    throw new PublicStorageMediaNotAvailableException();
  }
}
