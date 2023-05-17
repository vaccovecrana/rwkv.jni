package io.vacco.rwkv;

import java.io.*;
import java.nio.file.*;
import java.nio.file.StandardCopyOption;

public class RkNative {

  public static final String NATIVE_FOLDER_PATH_PREFIX = "rwkv-jni";

  private static File temporaryDir;

  public static void loadLibraryFromJar(String path) {
    try {
      var parts = path.split("/");
      var fName = (parts.length > 1) ? parts[parts.length - 1] : null;

      if (temporaryDir == null) {
        temporaryDir = new File(System.getProperty("java.io.tmpdir"), NATIVE_FOLDER_PATH_PREFIX);
        temporaryDir.deleteOnExit();
      }

      var temp = new File(temporaryDir, fName);
      temp.mkdirs();

      try (var is = RkNative.class.getResourceAsStream(path)) {
        Files.copy(is, temp.toPath(), StandardCopyOption.REPLACE_EXISTING);
        System.load(temp.getAbsolutePath());
      }
    } catch (Exception e) {
      throw new IllegalStateException("Unable to load native dependency: " + path, e);
    }
  }

}