plugins {
    id("city.android-lib")
    id("city.dagger-hilt")
}

android {
    namespace = "city.zouitel.remote"
}

dependencies {
    implementation(projects.data.repository)
}