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
    ":mobile"
)
include(
    ":common-ui"
)
include(
    "notification"
)
include(
    ":ui:tags",
    ":ui:record",
    ":ui:camera",
    ":ui:tasks",
    ":ui:media-player",
    ":ui:reminder"
)
include(
    ":domain"
)
include(
    ":data:local",
    ":data:datastore"
)
include(":ui:note")
