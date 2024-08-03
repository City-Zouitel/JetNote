@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.androidCompose)
    alias(libs.plugins.cityzouitel.androidNavigation)
}

android {
    namespace = "city.zouitel.systemDesign"
}

dependencies {
    //Modules.
    implementation(projects.core.datastore)

    //AndroidX.
    implementation(libs.constraintlayout)

    //Accompanist.
    implementation(libs.accompanist.permissions)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.accompanist.swiperefresh)

    //Baha-UrlPreview.
    implementation(libs.url.preview)

    //Balloon.
    implementation (libs.balloon.compose)
}