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
    ":core:database",
    ":core:repository",
    ":core:datastore"
)
include(":domain")
include(
    ":ui:features:note",
    ":ui:features:quickNote",
    ":ui:features:camera",
    ":ui:features:links",
    ":ui:features:audios",
    ":ui:features:recorder",
    ":ui:features:reminder",
    ":ui:features:tags",
    ":ui:features:tasks",
    ":ui:features:widget",
    ":ui:navigation",
    ":ui:common:systemDesign",
    ":ui:common:logic"
)
include(":services:notifications")
include(":init")
