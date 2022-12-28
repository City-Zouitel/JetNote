pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
//    versionCatalogs {
//        create("libraries") {
//            from(files("/gradle/libraries.versions.toml"))
//        }
//    }

}
rootProject.name = "JetNote"
include(":app")
