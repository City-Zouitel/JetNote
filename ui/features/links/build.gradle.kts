plugins {
    id("city.android-lib")
    id("city.compose-lib")
    id("city.dagger-hilt")
}

android {
    namespace = "com.example.links"
}

dependencies {

    //Projects.
    implementation(projects.common.ui)
    implementation(projects.domain)

    //AndroidX.
    implementation(libs.androidx.constraintlayout)

    //WorkManager.
    implementation(libs.androidx.workmanager)

    //Swipe.
    implementation(libs.swipe)

    //Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    //Baha-UrlPreview.
    implementation(libs.url.preview)
}