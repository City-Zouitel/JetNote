@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.androidCompose)
}

android {
    namespace = "city.zouitel.audios"
}

dependencies {
    //Modules.
    implementation(projects.core.datastore)
    implementation(projects.domain)
    implementation(projects.ui.common.systemDesign)

    //AndroidX.
    implementation(libs.constraintlayout)
}