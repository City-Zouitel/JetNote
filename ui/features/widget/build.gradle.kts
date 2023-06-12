plugins {
    id("city.android-lib")
    id("city.compose-lib")
    id("city.dagger-hilt")
}

android {
    namespace = "com.example.glance"
}

dependencies {
    //Projects.
    implementation(projects.domain)
    implementation(projects.common.commonUi)

    //AndroidX.
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.glance)
}