plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.composeVoyager)
}

android {
    namespace = "city.zouitel.playback"
}

dependencies {
    //Modules.
    implementation(projects.ui.features.player.audio)

    //Media3.
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.session)
    implementation(libs.media3.ui)

    //Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)
}
