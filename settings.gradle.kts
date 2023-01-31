enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

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
    versionCatalogs {
        create("libs") {
            from(files("gradle/libraries.versions.toml"))
        }
    }

}
rootProject.name = "JetNote"

include(
    ":data:local",
    ":data:datastore"
)
include(":domain")
include(":ui")
include(":mobile")
include(":ui:camera")
include(":common-ui")
include(":ui:tags")
