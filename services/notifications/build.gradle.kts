@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.androidCompose)
    alias(libs.plugins.cityzouitel.composeVoyager)
}

android {
    namespace = "city.zouitel.notifications"
}

dependencies {
    //Modules.
    implementation(projects.domain)
    implementation(projects.common.logic)

    //AndroidX.
    implementation(libs.constraintlayout)
}