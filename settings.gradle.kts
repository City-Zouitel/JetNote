enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        includeBuild("Script")
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
    ":app"
)
include(
    ":common:common-ui",
    ":common:effect",
    ":common:common-logic",
    ":common:mapper",
)
include(
    "service:notification"
)

include(
    ":ui:features:camera",
    ":ui:features:tags",
    ":ui:features:record",
    ":ui:features:tasks",
    ":ui:features:media-player",
    ":ui:features:reminder",
    ":ui:features:note",
    ":ui:features:widget",
    ":ui:features:quick-note",
    ":ui:features:links",
    ":ui:graph"
)
include(
    ":domain"
)
include(
    ":data:local",
    ":data:datastore",
    ":data:repository"
)
include(
    ":benchmark"
)
include(
    ":init"
)
include(
    ":api"
)
include(
    ":network"
)
