plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    alias(libs.plugins.ksp)
}
// features
android {
    namespace = "com.example.domain"
    compileSdk = 33

    defaultConfig {
        minSdk = 25
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(projects.data.local)
    implementation(projects.data.datastore)

    //AndroidX.
    implementation(libs.androidx.core)
    implementation(libs.androidx.corektx)
    implementation(libs.androidx.appcompat)

    //Dagger-Hilt
    implementation (libs.dagger)
    implementation (libs.dagger.hilt)
    implementation (libs.hilt.navcomp)
    kapt (libs.dagger.compiler)
    kapt (libs.hilt.compiler)
    kapt (libs.dagger.hiltcompiler)

    // ExoPlayer
    api(libs.exoplayer.core)
    api(libs.exoplayer.ui)
    api(libs.exoplayer.extension)

}