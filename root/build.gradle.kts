plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.androidNavigation)
    id("com.guardsquare.appsweep") version "latest.release"
}

android {
    namespace = "city.zouitel.root"
}

dependencies {
    implementation(projects.domain)
}