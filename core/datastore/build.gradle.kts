@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.android.library)
    alias(libs.plugins.cityzouitel.android.koin)
}

android {
    namespace = "city.zouitel.datastore"
}
dependencies {
    //Modules.
    implementation(projects.core.repository)

    //DataStore.
    implementation (libs.datastore)

    //AndroidX.
    implementation(libs.constraintlayout)

    //DS Security.
    implementation("io.github.osipxd:security-crypto-datastore-preferences:1.0.0-beta01")
}