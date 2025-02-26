plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.androidCompose)
    alias(libs.plugins.cityzouitel.composeVoyager)
}

android {
    namespace = "city.zouitel.audio"
}

dependencies {
    //Modules.
    implementation(projects.core.local.datastore)
    implementation(projects.domain)
    implementation(projects.common.systemDesign)
    implementation(projects.common.logic)
    implementation(projects.ui.features.player.waveform)

    //AndroidX.
    implementation(libs.constraintlayout)

    //Sliders.
    implementation(libs.amplituda)

    //Media3.
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.session)
    implementation(libs.media3.ui)
}