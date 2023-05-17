configure<io.vacco.oss.gitflow.GsPluginProfileExtension> {
  addJ8Spec()
}

val api by configurations

dependencies {
  api("ai.djl.huggingface:tokenizers:0.22.1")
  testImplementation(project(":rwkv-jni-linux-x86_64"))
}

tasks.withType<Test> {
  minHeapSize = "512m"
  maxHeapSize = "16384m"
}
