@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.androidCompose)
}

android {
    namespace = "city.zouitel.shortcuts"
}

dependencies {
    //Modules.
    implementation(projects.common.systemDesign)
    implementation(projects.common.logic)
    implementation(projects.ui.features.quickNote)
}