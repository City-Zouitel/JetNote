@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.androidCompose)
    alias(libs.plugins.cityzouitel.composeVoyager)
}

android {
    namespace = "city.zouitel.tasks"
}

dependencies {
    //Modules.
    implementation(projects.common.systemDesign)
    implementation(projects.common.logic)
    implementation(projects.domain)

    //AndroidX.
    implementation(libs.constraintlayout)
}