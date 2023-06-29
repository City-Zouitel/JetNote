plugins {
    id("city.android-lib")
    id("city.dagger-hilt")
    id("com.google.gms.google-services")
}

android {
    namespace = "city.zouitel.api"
}

dependencies {
    implementation(projects.domain)
    implementation(libs.androidx.workmanager)
    implementation("com.google.firebase:firebase-firestore-ktx:24.6.1")
}