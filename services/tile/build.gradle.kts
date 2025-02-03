plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
}

android {
    namespace = "city.zouitel.tile"
}

dependencies {
    //Modules.
    implementation(projects.ui.features.quickNote)
}