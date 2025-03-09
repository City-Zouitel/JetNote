plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.composeVoyager)
}

android {
    namespace = "city.zouitel.bin"
}

dependencies {
    //Modules.
    implementation(projects.domain)

    //Koin.
    implementation(libs.koin.worker)
}