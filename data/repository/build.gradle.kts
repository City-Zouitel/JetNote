plugins {
    id("city.android-lib")
    id("city.dagger-hilt")
}

android {
    namespace = "com.example.repository"
}

dependencies {

    //Project.
    implementation(projects.domain)

    //Kotlin-Coroutines.
    implementation(libs.kotlinx.coroutines.core)
}