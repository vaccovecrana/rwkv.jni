package io.vacco;

import ai.djl.huggingface.tokenizers.HuggingFaceTokenizer;
import io.vacco.rwkv.RkContext;
import io.vacco.rwkv.RkSample;
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

      var la = new long[1];

      try (var tok = HuggingFaceTokenizer.newInstance(Paths.get("./src/test/resources/20B_tokenizer.json"))) {
        var enc = tok.encode("+gen write a short poem");
        for (var tid : enc.getIds()) {
          RkContext.rwkvEval(rk.ctxPtr, (int) tid, rk.state, rk.state, rk.logits);

          la[0] = RkSample.sampleLogits(rk.logits, 0.8f, 0.5f, null);
          var tks = tok.decode(la);
          System.out.println(tks);
        }
      }

      RkContext.rwkvFree(rk.ctxPtr);
    });
  }
}
