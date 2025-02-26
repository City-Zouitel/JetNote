plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.androidCompose)
    alias(libs.plugins.cityzouitel.composeVoyager)
}

android {
    namespace = "city.zouitel.video"
}

dependencies {
    //Media3.
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.ui)
}