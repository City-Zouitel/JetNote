plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
}

android {
    namespace = "city.zouitel.connection"
}

dependencies {
    //Modules.
    implementation(projects.core.network.repository)
}