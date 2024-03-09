plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
}

android {
    namespace = "city.zouitel.security"
}

dependencies {
    implementation(projects.core.repository)
}