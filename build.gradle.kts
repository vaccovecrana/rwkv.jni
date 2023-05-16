plugins { id("io.vacco.oss.gitflow") version "0.9.8" }

group = "io.vacco.rwkv"
version = "0.1.0"

configure<io.vacco.oss.gitflow.GsPluginProfileExtension> {
  addJ8Spec()
  addClasspathHell()
  sharedLibrary(true, false)
}

val api by configurations

dependencies {
  implementation("ai.djl.huggingface:tokenizers:0.22.1")
}

