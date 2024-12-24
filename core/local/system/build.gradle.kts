plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
}

android {
    namespace = "city.zouitel.base"
}

dependencies {
    implementation(projects.core.local.repository)
}