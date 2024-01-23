enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
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
    }
}

rootProject.name = "JetNote"
include(":app")
include(":core:database")
include(":core:repository")
include(":core:datastore")
include(":domain")
include(":ui:features:note")
include(":ui:features:quickNote")
include(":ui:features:camera")
include(":ui:features:links")
include(":ui:features:audios")
include(":ui:features:recorder")
include(":ui:features:reminder")
include(":ui:features:tags")
include(":ui:features:tasks")
include(":ui:features:widget")
include(":ui:navigation")
include(":services:notifications")
include(":init")
include(":ui:common:systemDesign")
include(":ui:common:logic")
