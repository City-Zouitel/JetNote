plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    alias(libs.plugins.ksp)

}

android {
    namespace = "com.example.local"
    compileSdk = 33
    defaultConfig {
        minSdk = 25
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
kapt {
    correctErrorTypes = true

    arguments {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}
dependencies {

    //Room.
    implementation(libs.roomkts)
    api(libs.room.runtime)
    implementation("androidx.core:core-ktx:+")
    ksp(libs.room.compiler)

    //Dagger-Hilt
    implementation (libs.dagger)
    implementation (libs.dagger.hilt)
    implementation (libs.hilt.navcomp)
    kapt (libs.dagger.compiler)
    kapt (libs.hilt.compiler)
    kapt (libs.dagger.hiltcompiler)

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}