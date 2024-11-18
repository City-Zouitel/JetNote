@file:Suppress("UnstableApiUsage")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        includeBuild("source")
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

rootProject.name = "JetNote"

include(":app")
include(
    ":core:local:database",
    ":core:local:repository",
    ":core:local:datastore",
    ":core:network:ai",
    ":core:network:repository"
)
include(":domain")
include(
    ":ui:features:workplace",
    ":ui:features:quickNote",
    ":ui:features:camera",
    ":ui:features:links",
    ":ui:features:audios",
    ":ui:features:recorder",
    ":ui:features:reminder",
    ":ui:features:tags",
    ":ui:features:tasks",
    ":ui:features:widget",
    ":ui:features:media",
    ":ui:screens"
)
include(
    ":common:systemDesign",
    ":common:logic"
)
include(
    ":services:notifications",
    ":services:shortcuts"
)
include(":init")
include(
    ":security:encryption",
    ":security:rooted",
    ":security:screenshot",
    ":security:appLock"
)
