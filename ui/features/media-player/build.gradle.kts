plugins {
    id("city.android-lib")
    id("city.compose-lib")
    id("city.dagger-hilt")
}

android {
    namespace = "com.example.media_player"
}

dependencies {
    //Projects.
    implementation(projects.data.datastore)
    implementation(projects.domain)
    implementation(projects.common.commonUi)

    //AndroidX.
    implementation(libs.androidx.constraintlayout)
}