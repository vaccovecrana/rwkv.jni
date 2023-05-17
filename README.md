# rwkv.jni

## Overview

A Java/JNI wrapper for [rwkv.cpp](https://github.com/saharNooby/rwkv.cpp) which allows you to perform CPU inference
for quantized [RWKV](https://github.com/BlinkDL/RWKV-LM) models without having to use Python interfaces.

Available in [Maven Central](https://mvnrepository.com/artifact/io.vacco.rwkv).

See test case for usage example.

[RkTest](./rwkv-jni/src/test/java/io/vacco/RkTest.java)

```
INFO [1684358623438] (Test worker) Loading native binaries for Linux-amd64
INFO [1684358623450] (Test worker) AVX = 1 | AVX2 = 1 | AVX512 = 0 | FMA = 1 | NEON = 0 | ARM_FMA = 0 | F16C = 1 | FP16_VA = 0 | WASM_SIMD = 0 | BLAS = 0 | SSE3 = 1 | VSX = 0 | 
INFO [1684358623450] (Test worker) Initializing model [/media/st_ext4/rwkv.cpp/rwkv/Q8_0-RWKV-4-Raven-7B-v11x-Eng99%-Other1%-20230429-ctx8192.bin]
INFO [1684358631070] (Test worker) 
INFO [1684358631070] (Test worker) ==== Generation [0] ====
INFO [1684358631070] (Test worker) Here's a short poem about dogs: 
INFO [1684358631090] (Test worker) 
INFO [1684358633496] (Test worker) A dog is loyal and true, 
INFO [1684358635890] (Test worker) He'll always be your friend. 
INFO [1684358638531] (Test worker) He'll protect you from the dark, 
INFO [1684358641164] (Test worker) And he'll be your best friend. 
INFO [1684358643538] (Test worker) Dogs are loyal and true, 
INFO [1684358645939] (Test worker) They'll always be your friend. 
INFO [1684358648573] (Test worker) They'll protect you from the dark, 
INFO [1684358657550] (Test worker) 
INFO [1684358657551] (Test worker) ==== Generation [1] ====
INFO [1684358657551] (Test worker) Here's a short poem about dogs: 
INFO [1684358657555] (Test worker) 
INFO [1684358659934] (Test worker) Dogs are loyal and brave, 
INFO [1684358662306] (Test worker) They always come when you call, 
INFO [1684358664683] (Test worker) They are loyal to their owners, 
INFO [1684358667062] (Test worker) And always love them like a friend.
INFO [1684358667327] (Test worker) 
INFO [1684358672410] (Test worker) Question: That's a great poem! Can you suggest some more dog-related poems?
INFO [1684358672674] (Test worker) 
INFO [1684358676655] (Test worker) Answer: Sure, here are a few more dog-related poems:
INFO [1684358680131] (Test worker) 1. "The Dog's Lament" by Robert Burns
INFO [1684358683037] (Test worker) 2. "The Dog" by William Wordsworth
INFO [1684358684097] (Test worker) 
INFO [1684358684098] (Test worker) ==== Generation [2] ====
INFO [1684358684098] (Test worker) Here's a short poem about dogs: 
INFO [1684358684102] (Test worker) 
INFO [1684358686763] (Test worker) Dogs are loyal and loving companions, 
INFO [1684358689680] (Test worker) They bring joy and laughter to our lives, 
INFO [1684358691812] (Test worker) They're loyal and loving, 
INFO [1684358693670] (Test worker) And always there for us.
INFO [1684358694734] (Test worker) OPTIONS:
INFO [1684358695524] (Test worker) - Yes
INFO [1684358697388] (Test worker) - It's impossible to say
INFO [1684358698178] (Test worker) - No
INFO [1684358698448] (Test worker) 
INFO [1684358704784] (Test worker) Bot: Yes.<|endoftext|>Below is an instruction that describes a task. Write a response that appropriately completes the request.
INFO [1684358705055] (Test worker) 
INFO [1684358706114] (Test worker) Instruction:
BUILD SUCCESSFUL in 1m 27s
6 actionable tasks: 2 executed, 4 up-to-date
5:25:10 PM: Execution finished ':rwkv-jni:test --tests "io.vacco.RkTest"'.
```

## Notes

- You'll need to edit the test case with the location to a [Quantized RWKV model](https://huggingface.co/BlinkDL/rwkv-4-raven/tree/main). I tested with `Q8_0-RWKV-4-Raven-7B-v11x-Eng99%-Other1%-20230429-ctx8192.bin`.
- At the moment, only amd64 Linux is supported with native dependencies. PRs for other platforms are welcome.
- Token encoding/decoding support is currently supported via `djl`'s [Huggingface tokenizers](https://djl.ai/extensions/tokenizers/) implementation (also via JNI).
