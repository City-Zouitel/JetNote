@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.androidCompose)
    alias(libs.plugins.cityzouitel.composeVoyager)
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

    //Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    //Koin
    implementation(libs.koin.worker)

    //Metaprobe.
    implementation(libs.metaprobe.kmp)

    //Ktor.
    implementation(libs.ktor.client.android)
}