plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
}

android {
    namespace = "city.zouitel.generativeai"
}

dependencies {
    //Modules.
    implementation(projects.core.network.repository)

    //Gemini.
    implementation(libs.generativeai)

    //DataStore.
    implementation(libs.datastore)
}