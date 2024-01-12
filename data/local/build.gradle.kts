plugins {
    id("city.android-lib")
    id("city.dagger-hilt")
    id ("kotlin-kapt")
//    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.local"
}

kapt {
    arguments {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}
dependencies {

    //Modules.
    implementation(projects.data.repository)

    //Room.
    implementation(libs.roomkts)
    api(libs.room.runtime)
    kapt(libs.room.compiler)

    //Security.
    implementation (libs.androidx.security)

    //Sqlcipher.
    implementation (libs.sqlcipher)
}