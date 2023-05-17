package io.vacco.rwkv;

import java.util.*;

import static io.vacco.rwkv.RkUtil.*;

public class RkSample {

  public static int sampleProbs(float[] probs, float temperature, float topP, Map<Integer, Float> logitBias) {

    if (!(temperature >= 0.0)) {
      throw new IllegalArgumentException("Invalid temperature: " + temperature);
    }
    if (!(topP >= 0.0 && topP <= 1.0)) {
      throw new IllegalArgumentException("Invalid topP: " + topP);
    }
    if (topP == 0.0) {
      topP = 1.0f;
    }

    if (logitBias != null) {
      var logits = new float[probs.length];

      for (int i = 0; i < probs.length; i++) {
        logits[i] = (float) Math.log(probs[i]);
        if (logitBias.containsKey(i)) {
          logits[i] += logitBias.get(i);
        }
      }

      var sumExpLogits = expSum(logits);
      for (int i = 0; i < probs.length; i++) {
        probs[i] = (float) (Math.exp(logits[i]) / sumExpLogits);
      }
    }

    if (temperature == 0.0) {
      return argmax(probs);
    }

    if (topP < 1.0) {
      var sortedProbs = Arrays.copyOf(probs, probs.length);
      Arrays.sort(sortedProbs);
      sortedProbs = reverse(sortedProbs);

      var cumulativeProbs = cumulativeSum(sortedProbs);
      var cumulativeBools = new boolean[cumulativeProbs.length];

      for (int i = 0; i < cumulativeBools.length; i++) {
        cumulativeBools[i] = cumulativeProbs[i] > topP;
      }

      int cutoffIdx = argmax(cumulativeBools);
      var cutoff = sortedProbs[cutoffIdx];
      for (int i = 0; i < probs.length; i++) {
        if (probs[i] < cutoff) {
          probs[i] = 0.0f;
        }
      }
    }

    if (temperature != 1.0) {
      for (int i = 0; i < probs.length; i++) {
        probs[i] = (float) Math.pow(probs[i], 1.0 / temperature);
      }
    }

    double sumProbs = calculateSum(probs);
    for (int i = 0; i < probs.length; i++) {
      probs[i] /= sumProbs;
    }

    return randomChoice(probs);
  }

  public static int sampleLogits(float[] logits, float temperature, float topP, Map<Integer, Float> logitBias) {
    var probs = softmax(logits);
    return sampleProbs(probs, temperature, topP, logitBias);
  }

}