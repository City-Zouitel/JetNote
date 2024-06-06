@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.androidCompose)
    alias(libs.plugins.cityzouitel.androidNavigation)
}

android {
    namespace = "city.zouitel.reminder"
}

dependencies {
    //Modules.
    implementation(projects.common.systemDesign)
    implementation(projects.services.notifications)
    implementation(projects.core.datastore)

    //AndroidX.
    implementation(libs.constraintlayout)
}