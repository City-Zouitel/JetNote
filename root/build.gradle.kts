plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.composeVoyager)
    alias(libs.plugins.cityzouitel.androidCompose )
}

android {
    namespace = "city.zouitel.root"
}

dependencies {
    //Modules.
    implementation(projects.common.logic)
    implementation(projects.domain)
}