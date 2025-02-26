@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
}

android {
    namespace = "city.zouitel.localRepo"
}

dependencies {
    //Modules.
    implementation(projects.domain)

    //Kotlin-Coroutines.
    implementation(libs.coroutines.core)
}