plugins {
    id("city.android-lib")
    id("city.compose-lib")
    id("city.dagger-hilt")
}

android {
    namespace = "com.example.tasks"
}

dependencies {
    //Projects.
    implementation(projects.domain)
    implementation(projects.common.commonUi)

    //AndroidX.
    implementation(libs.androidx.constraintlayout)

    //Swipe.
    implementation (libs.swipe)
}