plugins {
    id("city.android-lib")
    id("city.dagger-hilt")
}

android {
    namespace = "city.zouitel.api"
}

dependencies {
    implementation(projects.domain)
    implementation(libs.androidx.workmanager)
}