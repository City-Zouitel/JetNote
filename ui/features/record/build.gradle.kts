plugins {
    id("city.android-lib")
    id("city.compose-lib")
    id("city.dagger-hilt")
}

android {
    namespace = "com.example.record"
}

dependencies {
    //Projects.
    implementation(projects.common.commonUi)
    implementation(projects.domain)
    implementation(projects.data.datastore)

    //AndroidX.
    implementation(libs.androidx.constraintlayout)
}