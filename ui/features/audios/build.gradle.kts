@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.androidCompose)
    alias(libs.plugins.cityzouitel.composeVoyager)
}

android {
    namespace = "city.zouitel.audios"
}

dependencies {
    //Modules.
    implementation(projects.core.local.datastore)
    implementation(projects.domain)
    implementation(projects.common.systemDesign)
    implementation(projects.common.logic)

    //AndroidX.
    implementation(libs.constraintlayout)

    //Accompanist.
    implementation(libs.accompanist.permissions)

    //Sliders.
//    implementation(libs.audiowaveform)
//    implementation(libs.amplituda)

    //Media3.
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.session)
    implementation(libs.media3.ui)
}