package ca.jbrains.upfp.model.android.test;

import android.os.Environment;
import ca.jbrains.upfp.controller.android.*;

import java.io.File;

public class AndroidDevicePublicStorageGatewayImpl
    implements AndroidDevicePublicStorageGateway {
  public String getExternalStorageState() {
    throw new UnsupportedOperationException();
  }

  public File getExternalStoragePublicDirectory() {
    throw new UnsupportedOperationException();
  }

  @Override
  public File findPublicExternalStorageDirectory()
      throws PublicStorageMediaNotAvailableException,
             PublicStorageMediaNotWritableException {
    if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(
        getExternalStorageState()))
      throw new PublicStorageMediaNotWritableException(
          getExternalStoragePublicDirectory());

    return getExternalStoragePublicDirectory();
  }
}
