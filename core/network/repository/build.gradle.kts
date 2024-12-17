plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.ksp)
}

android {
    namespace = "city.zouitel.networkRepo"
}

dependencies {
    //Modules.
    api(projects.domain)
}