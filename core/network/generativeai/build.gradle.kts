plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.ksp)
}

android {
    namespace = "city.zouitel.generativeai"
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

dependencies {
    //Modules.
    implementation(projects.core.network.networkRepo)

    //Gemini.
    implementation(libs.generativeai)

    //Room.
    implementation(libs.roomkts)
    api(libs.room.runtime)
    ksp(libs.room.compiler)

    //DataStore.
    implementation(libs.datastore)
}