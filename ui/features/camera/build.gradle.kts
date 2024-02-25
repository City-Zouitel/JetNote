@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.androidCompose)
}

android {
    namespace = "city.zouitel.camera"
}

dependencies {
    //Modules.
    implementation(projects.common.systemDesign)

    //AndroidX.
    implementation(libs.constraintlayout)

    //CameraX.
    implementation (libs.camerax.core)
    implementation (libs.camerax.lifecycle)
    implementation (libs.camerax.view)
    implementation (libs.camerax.extensions)
}