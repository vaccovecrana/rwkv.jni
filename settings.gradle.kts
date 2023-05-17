pluginManagement {
  repositories {
    mavenCentral()
    gradlePluginPortal()
  }
}

var osn = System.getProperty("os.name").toLowerCase()
var osa = System.getProperty("os.arch").toLowerCase()

include("rwkv-jni-${osn}-${osa}", "rwkv-jni")
