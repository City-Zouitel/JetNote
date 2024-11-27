@file:Suppress("UnstableApiUsage")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

pluginManagement {
    repositories {
        includeBuild("source")
        mavenCentral()
        gradlePluginPortal()
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("org\\.jetbrains.*")
            }
        }
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
    ":core:local:base",
    ":core:network:repository",
    ":core:network:generativeai"
)
include(":domain")
include(
    ":ui:features:assistant",
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
