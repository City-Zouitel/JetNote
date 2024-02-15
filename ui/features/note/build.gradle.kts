@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.androidCompose)
    alias(libs.plugins.ksp)
}

android {
    namespace = "city.zouitel.note"
}

dependencies {
    //Modules.
    implementation(projects.ui.common.systemDesign)
    implementation(projects.domain)
    implementation(projects.core.datastore)
    implementation(projects.ui.features.reminder)
    implementation(projects.ui.features.recorder)
    implementation(projects.ui.features.tasks)
    implementation(projects.ui.features.audios)
    implementation(projects.ui.features.tags)
    implementation(projects.ui.features.camera)
    implementation(projects.ui.features.links)

    //AndroidX.
    implementation(libs.constraintlayout)

    //Accompanist.
    implementation(libs.accompanist.permissions)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.accompanist.navigation.animation)
    implementation(libs.accompanist.swiperefresh)
    implementation(libs.accompanist.flowlayout)

    //Destinations.
    implementation(libs.destinations.core)
    ksp(libs.destinations.ksp)

    //Sketchbook.
    implementation (libs.sketchbook)

    //Balloon.
    implementation ("com.github.skydoves:balloon-compose:1.5.3")

    //Swipe.
    implementation (libs.swipe)

    //Baha-UrlPreview.
    implementation(libs.url.preview)
}