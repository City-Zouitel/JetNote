@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.androidCompose)
    alias(libs.plugins.cityzouitel.androidNavigation)
    id("com.guardsquare.appsweep") version "latest.release"

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
//    implementation(libs.accompanist.navigation.animation)
    implementation(libs.accompanist.swiperefresh)
    implementation(libs.accompanist.flowlayout)

    //Baha-UrlPreview.
    implementation(libs.url.preview)

    //Balloon.
    implementation ("com.github.skydoves:balloon-compose:1.6.4")
}