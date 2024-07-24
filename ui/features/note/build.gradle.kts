@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.androidCompose)
    alias(libs.plugins.cityzouitel.androidNavigation)
    alias(libs.plugins.ksp)
}

android {
    namespace = "city.zouitel.note"
}

dependencies {
    //Modules.
    implementation(projects.common.systemDesign)
    implementation(projects.common.logic)
    implementation(projects.domain)
    implementation(projects.core.datastore)
    implementation(projects.ui.features.reminder)
    implementation(projects.ui.features.recorder)
    implementation(projects.ui.features.tasks)
    implementation(projects.ui.features.audios)
    implementation(projects.ui.features.tags)
    implementation(projects.ui.features.camera)
    implementation(projects.ui.features.links)
    implementation(projects.ui.features.media)
    implementation(projects.services.notifications)

    //AndroidX.
    implementation(libs.constraintlayout)

    //Accompanist.
    implementation(libs.accompanist.permissions)
    implementation(libs.accompanist.systemuicontroller)
//    implementation(libs.accompanist.navigation.animation)
//    implementation(libs.accompanist.flowlayout)

    //Sketchbook.
    implementation (libs.sketchbook)

    //Balloon.
    implementation ("com.github.skydoves:balloon-compose:1.6.4")

    //Swipe.
    implementation (libs.swipe)

    //Baha-UrlPreview.
    implementation(libs.url.preview)

    //Coil.
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)
}