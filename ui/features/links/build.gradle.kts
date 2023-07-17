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
    implementation(projects.common.commonUi)
    implementation(projects.domain)

    //AndroidX.
    implementation(libs.androidx.constraintlayout)
    
    //Lifecycle.
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

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