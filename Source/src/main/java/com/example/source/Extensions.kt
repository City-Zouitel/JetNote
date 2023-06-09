package com.example.source

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.CompileOptions
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion


fun CommonExtension<*,*,*,*>.androidConfig() {
    defaultConfig {
        compileSdk = Versions.compileSdk
        minSdk = Versions.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }



}

fun CommonExtension<*,*,*,*>.composeConfig() {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeVersion
    }
}

fun LibraryExtension.proguardConfig() {
    buildTypes {
        release {
            isMinifyEnabled = true
//            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

}
