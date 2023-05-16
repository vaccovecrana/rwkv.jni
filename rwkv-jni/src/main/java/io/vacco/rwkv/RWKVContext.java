package io.vacco.rwkv;

public class RWKVContext {

  static {
    // TODO fix this
    System.load("/home/jjzazuet/code/rwkv.jni/rwkv-jni-linux-x86_64/src/main/resources/io/vacco/rwkv/librwkv.so");
    System.load("/home/jjzazuet/code/rwkv.jni/rwkv-jni-linux-x86_64/src/main/resources/io/vacco/rwkv/librwkv_context.so");
  }

  public static native long rwkvInitFromFile(String modelFilePath, int numThreads);
  public static native boolean rwkvEval(long contextPtr, int token, float[] stateIn, float[] stateOut, float[] logitsOut);
  public static native int rwkvGetStateBufferElementCount(long contextPtr);
  public static native int rwkvGetLogitsBufferElementCount(long contextPtr);
  public static native void rwkvFree(long contextPtr);
  public static native boolean rwkvQuantizeModelFile(String modelFilePathIn, String modelFilePathOut, String formatName);
  public static native String rwkvGetSystemInfoString();

}