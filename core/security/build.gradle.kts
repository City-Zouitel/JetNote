plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    id("com.guardsquare.appsweep") version "latest.release"

}

android {
    namespace = "city.zouitel.security"
}

dependencies {
    implementation(projects.core.repository)
}