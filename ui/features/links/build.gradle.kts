@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.androidCompose)
    alias(libs.plugins.cityzouitel.androidNavigation)
}

android {
    namespace = "city.zouitel.links"
}

dependencies {
    //Modules.
    implementation(projects.common.systemDesign)
    implementation(projects.common.logic)
    implementation(projects.domain)

    //AndroidX.
    implementation(libs.constraintlayout)

    //WorkManager.
    implementation(libs.workmanager)

    //Swipe.
    implementation(libs.swipe)

    //Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    //Baha-UrlPreview.
    implementation(libs.url.preview)

    //Koin
    implementation(libs.koin.worker)
}