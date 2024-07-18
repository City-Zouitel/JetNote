@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.androidCompose)
}

android {
    namespace = "city.zouitel.init"
}

dependencies {
    //Modules.
    implementation(projects.common.systemDesign)

    //Koin.
    implementation(libs.koin.worker)

    //Lifecycle.
    implementation(libs.lifecycle.process)

    //Worker.
    implementation(libs.workmanager)

    //Koin.
    implementation(libs.koin.worker)

    //Sqlcipher.
    implementation (libs.sqlcipher)
    implementation("androidx.sqlite:sqlite-ktx:2.4.0")
}