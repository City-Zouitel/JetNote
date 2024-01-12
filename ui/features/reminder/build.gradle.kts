plugins {
    id("city.android-lib")
    id("city.compose-lib")
    id("city.dagger-hilt")
}

android {
    namespace = "com.example.reminder"
}

dependencies {
    //Projects.
    implementation(projects.common.ui)
    implementation(projects.service.notification)
    implementation(projects.data.datastore)

    //AndroidX.
    implementation(libs.androidx.constraintlayout)
}