plugins {
    id("city.android-lib")
    id("city.dagger-hilt")
}

android {
    namespace = "com.example.notification"
}

dependencies {
    //Projects.
    implementation(projects.common.ui)

    //AndroidX.
    implementation(libs.androidx.constraintlayout)
}