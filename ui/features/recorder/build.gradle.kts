@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.androidCompose)
    alias(libs.plugins.cityzouitel.composeVoyager)
}

android {
    namespace = "city.zouitel.recorder"
}

dependencies {
    //Modules.
    implementation(projects.domain)
    implementation(projects.common.systemDesign)
    implementation(projects.common.logic)
    implementation(projects.core.local.datastore)

    //AndroidX.
    implementation(libs.constraintlayout)
}