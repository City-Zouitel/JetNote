@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.androidCompose)
    alias(libs.plugins.cityzouitel.composeVoyager)
    alias(libs.plugins.ksp)
}

android {
    namespace = "city.zouitel.note"
}

dependencies {
    //Modules.
    implementation(projects.common.systemDesign)
    implementation(projects.common.logic)
    implementation(projects.common.permissions)
    implementation(projects.domain)
    implementation(projects.core.local.datastore)
    implementation(projects.ui.features.reminder)
    implementation(projects.ui.features.recorder)
    implementation(projects.ui.features.tasks)
    implementation(projects.ui.features.player.audio)
    implementation(projects.ui.features.tags)
    implementation(projects.ui.features.camera)
    implementation(projects.ui.features.links)
    implementation(projects.ui.features.media)
    implementation(projects.services.notifications)

    //AndroidX.
    implementation(libs.constraintlayout)

    //Sketchbook.
    implementation (libs.sketchbook)

    //Balloon.
    implementation (libs.balloon.compose)
    //Cloudy.
    implementation(libs.compose.cloudy)

    //Coil.
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)
    implementation(libs.zoomable.coil)

    //Koin.
    implementation(libs.koin.worker)
}