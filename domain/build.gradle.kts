plugins {
    id("city.android-lib")
    id("city.dagger-hilt")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.domain"
}

dependencies {

    //Projects.
    implementation(projects.data.datastore)

    //ExoPlayer
    api(libs.exoplayer.core)
    api(libs.exoplayer.ui)
    api(libs.exoplayer.extension)
}