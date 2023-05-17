configure<io.vacco.oss.gitflow.GsPluginProfileExtension> {
  addJ8Spec()
}

val api by configurations

var osn = System.getProperty("os.name").toLowerCase()
var osa = System.getProperty("os.arch").toLowerCase()

dependencies {
  api("ai.djl.huggingface:tokenizers:0.22.1")
  testImplementation(project(":rwkv-jni-${osn}-${osa}"))
  testImplementation("io.vacco.shax:shax:1.7.30.0.0.7")
}

tasks.withType<Test> {
  minHeapSize = "512m"
  maxHeapSize = "16384m"
}
