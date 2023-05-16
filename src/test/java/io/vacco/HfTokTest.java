package io.vacco;

import ai.djl.huggingface.tokenizers.HuggingFaceTokenizer;

import j8spec.annotation.DefinedOrder;
import j8spec.junit.J8SpecRunner;
import org.junit.runner.RunWith;

import java.nio.file.Paths;

import static j8spec.J8Spec.*;

@DefinedOrder
@RunWith(J8SpecRunner.class)
public class HfTokTest {
  static {
    it("lols", () -> {
      var tok = HuggingFaceTokenizer.newInstance(Paths.get("./src/test/resources/20B_tokenizer.json"));
      var enc = tok.encode("Hello papa Gundam!");

      tok.close();


      System.out.println("lel");
    });
  }
}
