package io.vacco.rwkv;

import static java.lang.String.*;

public class RkPrompts {

  public static String generatePrompt(String instruction, String input) {
    if (input != null) {
      return format(
        "Below is an instruction that describes a task, paired with an input that provides further context. Write a response that appropriately completes the request.%n%n"
        + "# Instruction:%n%s%n%n"
        + "# Input:%n%s%n%n"
        + "# Response:%n", instruction, input);
    } else {
      return format("Below is an instruction that describes a task. Write a response that appropriately completes the request.%n%n"
        + "# Instruction:%n%s%n%n"
        + "# Response:%n", instruction);
    }
  }

}
