@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.androidCompose)
    alias(libs.plugins.cityzouitel.composeVoyager)
}

android {
    namespace = "city.zouitel.widget"
}

dependencies {
    //Modules.
    implementation(projects.domain)
    implementation(projects.common.systemDesign)

    //AndroidX.
    implementation(libs.constraintlayout)
    implementation(libs.glance)
    implementation(projects.ui.features.workplace)
    implementation(projects.ui.features.tags)
    implementation(projects.ui.features.tasks)
    implementation(projects.ui.features.links)
    implementation(projects.ui.features.media)

    //Coil.
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)
}