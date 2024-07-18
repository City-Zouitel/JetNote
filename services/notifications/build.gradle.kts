@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.androidCompose)
    alias(libs.plugins.cityzouitel.androidNavigation)
    id("com.guardsquare.appsweep") version "latest.release"

}

android {
    namespace = "city.zouitel.notifications"
}

dependencies {
    //AndroidX.
    implementation(libs.constraintlayout)
}