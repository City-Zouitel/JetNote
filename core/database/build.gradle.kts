@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.ksp)
    id("com.guardsquare.appsweep") version "latest.release"

}

android {
    namespace = "city.zouitel.database"
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

dependencies {
    //Modules.
    implementation(projects.core.repository)

    //Room.
    implementation(libs.roomkts)
    api(libs.room.runtime)
    ksp(libs.room.compiler)

    //Security.
    implementation (libs.security)

    //Sqlcipher.
    implementation (libs.sqlcipher)
}