@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.androidCompose)
}

android {
    namespace = "city.zouitel.tasks"
}

dependencies {
    //Modules.
    implementation(projects.ui.common.systemDesign)
    implementation(projects.domain)

    //AndroidX.
    implementation(libs.constraintlayout)

    //Swipe.
    implementation (libs.swipe)
}