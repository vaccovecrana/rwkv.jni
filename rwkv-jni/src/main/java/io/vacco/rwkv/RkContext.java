package io.vacco.rwkv;

import java.io.File;

public class RkContext {

  static {
    // TODO fix this
    var rkLib = new File("/home/jjzazuet/code/rwkv.jni/rwkv-jni-linux-x86_64/src/main/resources/io/vacco/rwkv/librwkv.so");
    var jniLib = new File("/home/jjzazuet/code/rwkv.jni/rwkv-jni-linux-x86_64/src/main/resources/io/vacco/rwkv/librwkv_context.so");

    System.out.println(rkLib.exists());
    System.out.println(jniLib.exists());

    System.load(rkLib.getAbsolutePath());
    System.load(jniLib.getAbsolutePath());
  }

  public static native long rwkvInitFromFile(String modelFilePath, int numThreads);
  public static native boolean rwkvEval(long contextPtr, int token, float[] stateIn, float[] stateOut, float[] logitsOut);
  public static native int rwkvGetStateBufferElementCount(long contextPtr);
  public static native int rwkvGetLogitsBufferElementCount(long contextPtr);
  public static native void rwkvFree(long contextPtr);
  public static native boolean rwkvQuantizeModelFile(String modelFilePathIn, String modelFilePathOut, String formatName);
  public static native String rwkvGetSystemInfoString();

  public static Rk init(File modelPath, int numThreads) {
    var rk = new Rk();
    rk.modelPath = modelPath.getAbsolutePath();
    rk.ctxPtr = rwkvInitFromFile(modelPath.getAbsolutePath(), numThreads);
    rk.state = new float[rwkvGetStateBufferElementCount(rk.ctxPtr)];
    rk.logits = new float[rwkvGetLogitsBufferElementCount(rk.ctxPtr)];
    return rk;
  }

}