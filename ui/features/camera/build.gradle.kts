plugins {
//    id("com.android.library")
//    id("org.jetbrains.kotlin.android")
    id("city.android-lib")
    id("city.compose-lib")
//    id("city.dagger-hilt")
}

android {
    namespace = "com.example.camera"
//    compileSdk = 33
//
//    defaultConfig {
//        minSdk = 25
//        targetSdk = 33
//
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        consumerProguardFiles("consumer-rules.pro")
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
//    buildFeatures {
//        compose = true
//    }
//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.4.0"
//    }
//    kotlinOptions {
//        jvmTarget = "1.8"
//    }
}

dependencies {
    implementation(projects.common.commonUi)
    //AndroidX.
//    implementation(libs.androidx.core)
//    implementation(libs.androidx.corektx)
//    implementation(libs.androidx.appcompat)
//    implementation(libs.androidx.runtimektx)
    implementation(libs.androidx.constraintlayout)

    //Compose.
    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
    implementation(libs.compose.activity)
    implementation(libs.compose.navigation)
    implementation(libs.compose.toolingpreview)
    implementation(libs.compose.viewmodel)
    implementation(libs.compose.constraintlayout)
    // CameraX
    implementation (libs.camerax.core)
    implementation (libs.camerax.lifecycle)
    implementation (libs.camerax.view)
    implementation (libs.camerax.extensions)

}