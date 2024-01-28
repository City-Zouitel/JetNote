@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.androidCompose)
}

android {
    namespace = "city.zouitel.init"
}

dependencies {
    //Koin.
    implementation(libs.koin.worker)

    implementation(libs.startup)

    //Lifecycle.
    implementation(libs.lifecycle.process)

    //
    implementation(libs.workmanager)

    //Sqlcipher.
    implementation (libs.sqlcipher)
    implementation("androidx.sqlite:sqlite-ktx:2.4.0")
}