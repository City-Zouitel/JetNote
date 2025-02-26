@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.androidCompose)
    alias(libs.plugins.cityzouitel.composeVoyager)
}

android {
    namespace = "city.zouitel.systemDesign"
}

dependencies {
    //Modules.
    implementation(projects.core.local.datastore)

    //AndroidX.
    implementation(libs.constraintlayout)

    //Balloon.
    implementation (libs.balloon.compose)
}