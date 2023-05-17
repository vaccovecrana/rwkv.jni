package io.vacco;

import io.vacco.rwkv.RkContext;
import j8spec.annotation.DefinedOrder;
import j8spec.junit.J8SpecRunner;
import org.junit.runner.RunWith;

import java.io.File;

import static j8spec.J8Spec.*;

@DefinedOrder
@RunWith(J8SpecRunner.class)
public class RkTest {
  static {
    it("Loads an RWKV model", () -> {
      var modelFile = new File("/media/st_ext4/rwkv.cpp/rwkv/Q8_0-RWKV-4-Raven-7B-v11x-Eng99%-Other1%-20230429-ctx8192.bin");
      var rk = RkContext.init(modelFile, 4);

      System.out.println("lel");

      RkContext.rwkvFree(rk.ctxPtr);
    });
  }
}
