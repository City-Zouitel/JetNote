@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.android.library)
    alias(libs.plugins.cityzouitel.android.koin)
}

android {
    namespace = "city.zouitel.repository"
}

dependencies {
    //Modules.
    implementation(projects.domain)

    //Kotlin-Coroutines.
    implementation(libs.coroutines.core)
}