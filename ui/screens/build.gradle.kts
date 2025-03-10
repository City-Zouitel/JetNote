@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.androidCompose)
    alias(libs.plugins.cityzouitel.composeVoyager)
    alias(libs.plugins.ksp)
    alias(libs.plugins.licenses)
}

android {
    namespace = "city.zouitel.screens"
}

dependencies {
    //Modules.
    implementation(projects.core.local.datastore)
    implementation(projects.core.local.database)
    implementation(projects.domain)
    implementation(projects.services.notifications)
    implementation(projects.common.systemDesign)
    implementation(projects.common.logic)
    api(projects.ui.features.assistant)
    api(projects.ui.features.tags)
    api(projects.ui.features.camera)
    api(projects.ui.features.recorder)
    api(projects.ui.features.tasks)
    api(projects.ui.features.player.audio)
    api(projects.ui.features.player.video)
    api(projects.ui.features.reminder)
    api(projects.ui.features.workplace)
    api(projects.ui.features.quickNote)
    api(projects.ui.features.links)
    api(projects.ui.features.widget)
    api(projects.ui.features.media)
    implementation(projects.security.rooted)
    implementation(projects.security.appLock)

    //AndroidX.
    implementation(libs.constraintlayout)

    //Compose.
    implementation(libs.compose.material)

    //LiveData.
    implementation(libs.lifecycle.livedata)

    //Sketchbook.
    implementation (libs.sketchbook)

    //Balloon.
    implementation (libs.balloon.compose)
    //Cloudy.
    implementation(libs.compose.cloudy)

    //Coil.
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    //License.
    implementation (libs.licenses)

    //Beetle.
    implementation(libs.beetle)
}