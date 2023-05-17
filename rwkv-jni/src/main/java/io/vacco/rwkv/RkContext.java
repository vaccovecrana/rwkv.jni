package io.vacco.rwkv;

import com.sun.jna.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;

public class RkContext {

  private static final Logger log = LoggerFactory.getLogger(RkContext.class);

  static {
    var os = String.format("%s-%s", System.getProperty("os.name"), System.getProperty("os.arch"));
    log.info("Loading native binaries for {}", os);
    if (Platform.isLinux() && Platform.is64Bit()) {
      RkNative.loadLibraryFromJar("/io/vacco/rwkv/librwkv.so");
      RkNative.loadLibraryFromJar("/io/vacco/rwkv/librwkv_context.so");
    } else {
      var msg = String.format("No native binaries available for [%s]. PRs are welcome :)", os);
      throw new UnsupportedOperationException(msg);
    }
  }

  public static native long rwkvInitFromFile(String modelFilePath, int numThreads);
  public static native boolean rwkvEval(long contextPtr, int token, float[] stateIn, float[] stateOut, float[] logitsOut);
  public static native int rwkvGetStateBufferElementCount(long contextPtr);
  public static native int rwkvGetLogitsBufferElementCount(long contextPtr);
  public static native void rwkvFree(long contextPtr);
  public static native boolean rwkvQuantizeModelFile(String modelFilePathIn, String modelFilePathOut, String formatName);
  public static native String rwkvGetSystemInfoString();

  public static Rk init(File model, int numThreads) {
    log.info("Initializing model [{}]", model.getAbsolutePath());
    var rk = new Rk();
    rk.modelPath = model.getAbsolutePath();
    rk.ctxPtr = rwkvInitFromFile(model.getAbsolutePath(), numThreads);
    rk.state = new float[rwkvGetStateBufferElementCount(rk.ctxPtr)];
    rk.logits = new float[rwkvGetLogitsBufferElementCount(rk.ctxPtr)];
    return rk;
  }

}