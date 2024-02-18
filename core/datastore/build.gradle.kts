@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
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
    implementation(libs.datastore.security)
}