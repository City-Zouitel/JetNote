plugins {
    id("city.android-lib")
//    id("com.android.library")
//    id("org.jetbrains.kotlin.android")
    id("city.dagger-hilt")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.local"
//    compileSdk = 33
//    defaultConfig {
//        minSdk = 25
//
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
//    kotlinOptions {
//        jvmTarget = "1.8"
//    }
}
kapt {
    correctErrorTypes = true

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
    ksp(libs.room.compiler)

    //Dagger-Hilt
    implementation (libs.dagger)
    implementation (libs.dagger.hilt)
    implementation (libs.hilt.navcomp)
    kapt (libs.dagger.compiler)
    kapt (libs.hilt.compiler)
    kapt (libs.dagger.hiltcompiler)

    //Security.
    implementation (libs.androidx.security)

    //Sqlcipher.
    implementation (libs.sqlcipher)

}