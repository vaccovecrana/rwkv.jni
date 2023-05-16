plugins { id("io.vacco.oss.gitflow") version "0.9.8" apply(false) }

subprojects {
  apply(plugin = "io.vacco.oss.gitflow")
  group = "io.vacco.rwkv"
  version = "1c363e6" // in sync with https://github.com/saharNooby/rwkv.cpp/releases

  configure<io.vacco.oss.gitflow.GsPluginProfileExtension> {
    addClasspathHell()
    sharedLibrary(true, false)
  }

  configure<io.vacco.cphell.ChPluginExtension> {
    resourceExclusions.add("module-info.class")
  }
}
