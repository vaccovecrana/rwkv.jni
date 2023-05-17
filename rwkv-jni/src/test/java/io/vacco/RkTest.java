package io.vacco;

import ai.djl.huggingface.tokenizers.HuggingFaceTokenizer;
import io.vacco.rwkv.*;
import j8spec.annotation.DefinedOrder;
import j8spec.junit.J8SpecRunner;
import org.junit.runner.RunWith;
import java.io.File;
import java.nio.file.Paths;

import static j8spec.J8Spec.*;

@DefinedOrder
@RunWith(J8SpecRunner.class)
public class RkTest {
  static {
    it("Loads an RWKV model", () -> {
      System.out.println(RkContext.rwkvGetSystemInfoString());

      var modelFile = new File("/media/st_ext4/rwkv.cpp/rwkv/Q8_0-RWKV-4-Raven-7B-v11x-Eng99%-Other1%-20230429-ctx8192.bin");
      var rk = RkContext.init(modelFile, 4);

      try (var tok = HuggingFaceTokenizer.newInstance(Paths.get("./src/test/resources/20B_tokenizer.json"))) {
        var prompt = "Here's a short poem about cats: ";
        var enc = tok.encode(prompt);
        for (var tid : enc.getIds()) {
          RkContext.rwkvEval(rk.ctxPtr, (int) tid, rk.state, rk.state, rk.logits);
        }

        var la = new long[1];
        var initState = new float[rk.state.length];
        var initLogits = new float[rk.logits.length];

        System.arraycopy(rk.state, 0, initState, 0, rk.state.length);
        System.arraycopy(rk.logits, 0, initLogits, 0, rk.logits.length);

        for (int i = 0; i < 3; i++) {
          System.out.println("==== Generation " + i);
          System.arraycopy(initState, 0, rk.state, 0, rk.state.length);
          System.arraycopy(initLogits, 0, rk.logits, 0, rk.logits.length);
          System.out.println(prompt);

          for (int j = 0; j < 100; j++) {
            la[0] = RkSample.sampleLogits(rk.logits, 0.8f, 0.5f, null);
            System.out.printf("%s", tok.decode(la));
            System.out.flush();
            RkContext.rwkvEval(rk.ctxPtr, (int) la[0], rk.state, rk.state, rk.logits);
          }
          System.out.println();
        }
      }

      RkContext.rwkvFree(rk.ctxPtr);
    });
  }
}
