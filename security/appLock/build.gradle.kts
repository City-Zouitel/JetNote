
plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.androidCompose)
    alias(libs.plugins.cityzouitel.composeVoyager)
}

android {
    namespace = "city.zouitel.applock"
}

dependencies {
    //Modules.
    implementation(projects.common.systemDesign)

    //Biometric.
    implementation(libs.androidx.biometric)

    //Lifecycle.
    implementation(libs.lifecycle.compose.runtime)
}
