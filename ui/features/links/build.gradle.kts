plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.links"
    compileSdk = 33

    defaultConfig {
        minSdk = 25

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
        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = "1.4.0"
        }
    }
}

dependencies {

    implementation(projects.common.commonUi)
    implementation(projects.domain)

    //AndroidX.
    implementation(libs.androidx.core)
    implementation(libs.androidx.corektx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.runtimektx)
    implementation(libs.androidx.constraintlayout)

    //Compose.
    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
    implementation(libs.compose.activity)
    implementation(libs.compose.navigation)
    implementation(libs.compose.toolingpreview)
    implementation(libs.compose.viewmodel)
    implementation(libs.compose.constraintlayout)

    //Dagger-Hilt
    implementation(libs.dagger)
    implementation(libs.dagger.hilt)
    implementation(libs.hilt.navcomp)
    implementation(libs.hilt.work)
    kapt(libs.dagger.compiler)
    kapt(libs.hilt.compiler)
    kapt(libs.dagger.hiltcompiler)

    //WorkManager.
    implementation(libs.androidx.workmanager)

    //Swipe.
    implementation(libs.swipe)

    //Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    //Baha-UrlPreview.
    implementation(libs.url.preview)
}