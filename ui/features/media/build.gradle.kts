plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.androidCompose)
    alias(libs.plugins.cityzouitel.composeVoyager)
}

android {
    namespace = "city.zouitel.media"
}

dependencies {
    //Modules.
    implementation(projects.domain)
    implementation(projects.common.logic)
    implementation(projects.ui.features.player.audio)

    //Coil.
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)
    implementation(libs.zoomable.coil)
    implementation(project(":ui:features:player:video"))
}