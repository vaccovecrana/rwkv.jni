package io.vacco.rwkv;

import java.nio.ByteBuffer;

public class RWKVContext {

  static {
    System.loadLibrary("rwkv");
  }

  private native long rwkvInitFromFile(String modelFilePath, int numThreads);
  private native boolean rwkvEval(long contextPtr, int token, float[] stateIn, float[] stateOut, float[] logitsOut);
  private native int rwkvGetStateBufferElementCount(long contextPtr);
  private native int rwkvGetLogitsBufferElementCount(long contextPtr);
  private native void rwkvFree(long contextPtr);
  private static native boolean rwkvQuantizeModelFile(String modelFilePathIn, String modelFilePathOut, String formatName);
  private static native String rwkvGetSystemInfoString();

}