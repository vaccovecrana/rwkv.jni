package io.vacco.rwkv;

public class RkUtil {

  public static float[] softmax(float[] input) {
    var out = new float[input.length];
    var sum = 0.0f;
    for (var value : input) {
      sum += Math.exp(value);
    }
    for (int i = 0; i < input.length; i++) {
      out[i] = (float) (Math.exp(input[i]) / sum);
    }
    return out;
  }

  public static float calculateSum(float[] in) {
    var sum = 0.0f;
    for (float num : in) {
      sum += num;
    }
    return sum;
  }

  public static int argmax(boolean[] in) {
    for (int i = 0; i < in.length; i++) {
      if (in[i]) {
        return i;
      }
    }
    return -1; // Not found
  }

  public static int argmax(float[] in) {
    int argmax = 0;
    var maxVal = in[0];
    for (int i = 1; i < in.length; i++) {
      if (in[i] > maxVal) {
        argmax = i;
        maxVal = in[i];
      }
    }
    return argmax;
  }

  public static float[] reverse(float[] in) {
    var reversed = new float[in.length];
    for (int i = 0; i < in.length; i++) {
      reversed[i] = in[in.length - i - 1];
    }
    return reversed;
  }

  public static float[] cumulativeSum(float[] in) {
    var cumulative = new float[in.length];
    cumulative[0] = in[0];
    for (int i = 1; i < in.length; i++) {
      cumulative[i] = cumulative[i - 1] + in[i];
    }
    return cumulative;
  }

  public static float expSum(float[] in) {
    var exp = new float[in.length];
    for (int i = 0; i < in.length; i++) {
      exp[i] = (float) Math.exp(in[i]);
    }
    return calculateSum(exp);
  }

  public static int randomChoice(float[] in) {
    double rand = Math.random();
    double cumulative = 0.0;
    for (int i = 0; i < in.length; i++) {
      cumulative += in[i];
      if (rand < cumulative) {
        return i;
      }
    }
    return in.length - 1; // Fallback (shouldn't happen)
  }

}
