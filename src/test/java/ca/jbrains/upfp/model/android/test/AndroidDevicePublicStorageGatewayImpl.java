package ca.jbrains.upfp.model.android.test;

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
    return getExternalStoragePublicDirectory();
  }
}
