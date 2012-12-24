package ca.jbrains.upfp.controller.android;

import java.io.File;

public class PublicStorageMediaNotWritableException
    extends Exception {

  private final File pathNotWritable;

  public PublicStorageMediaNotWritableException(
      File pathNotWritable
  ) {
    this.pathNotWritable = pathNotWritable;
  }

  public File getPathNotWritable() {
    return pathNotWritable;
  }
}
