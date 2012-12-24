package ca.jbrains.upfp.controller.android;

import java.io.File;

public interface AndroidDevicePublicStorageGateway {
  File findPublicExternalStorageDirectory()
      throws PublicStorageMediaNotAvailableException,
             PublicStorageMediaNotWritableException;
}
