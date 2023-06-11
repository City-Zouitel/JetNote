plugins {
    id("city.android-lib")
//    id("com.android.library")
//    id("org.jetbrains.kotlin.android")
    id("city.dagger-hilt")
//    id ("kotlin-kapt")
//    id ("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.repository"
//    compileSdk = 33
//
//    defaultConfig {
//        minSdk = 25
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//        }
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
//    kotlinOptions {
//        jvmTarget = "1.8"
//    }
}

//hilt {
//    enableAggregatingTask = true
//}

dependencies {

    //Project.
    implementation(projects.domain)

    //Dagger-Hilt.
//    implementation (libs.dagger)
//    implementation (libs.dagger.hilt)
//    kapt (libs.dagger.compiler)
//    kapt (libs.hilt.compiler)
//    kapt (libs.dagger.hiltcompiler)

    //Kotlin-Coroutines.
    implementation(libs.kotlinx.coroutines.core)
}