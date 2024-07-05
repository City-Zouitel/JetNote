plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.androidCompose)
    alias(libs.plugins.cityzouitel.androidNavigation)
}

android {
    namespace = "city.zouitel.media"
}

dependencies {
    //Modules.
    implementation(projects.domain)

    //Coil.
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)
}