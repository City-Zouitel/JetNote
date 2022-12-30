
plugins {
    id("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.jetnote"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.jetnote"
        minSdk = 25
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isShrinkResources = true
            isMinifyEnabled = true
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
    buildFeatures {
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}
//
kapt {
    correctErrorTypes = true

    arguments {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}
//
dependencies {
    //AndroidX.
    implementation(libs.androidx.core)
    implementation(libs.androidx.corektx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.runtimektx)
    implementation(libs.androidx.constraintlayout)


    //Compose.
    implementation (libs.compose.ui)
    implementation (libs.compose.material3)
    implementation (libs.compose.activity)
    implementation (libs.compose.navigation)
    implementation (libs.compose.toolingpreview)
    implementation (libs.compose.viewmodel)
    implementation(libs.compose.constraintlayout)

    //Room.
    implementation ("androidx.room:room-ktx:2.4.3")
    implementation ("androidx.room:room-runtime:2.4.3")
    annotationProcessor ("androidx.room:room-compiler:2.4.3")
    kapt ("androidx.room:room-compiler:2.4.3")

    //DataStore.
    implementation ("androidx.datastore:datastore-preferences:1.0.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")

    //Dagger.
    implementation ("com.google.dagger:dagger:2.42")
    annotationProcessor ("com.google.dagger:dagger-compiler:2.42")
    //Hilt.
    implementation ("com.google.dagger:hilt-android:2.42")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")
    kapt ("androidx.hilt:hilt-compiler:1.0.0")
    kapt ("com.google.dagger:hilt-compiler:2.42")

    //Sketchbook.
    implementation ("io.getstream:sketchbook:1.0.4")

    //Accompanist.
    implementation ("com.google.accompanist:accompanist-permissions:0.28.0")
    implementation ("com.google.accompanist:accompanist-pager:0.24.11-rc")
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.24.11-rc")
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.24.11-rc")
    implementation ("com.google.accompanist:accompanist-navigation-animation:0.24.11-rc")
    implementation ("com.google.accompanist:accompanist-swiperefresh:0.24.11-rc")
    implementation ("com.google.accompanist:accompanist-flowlayout:0.24.11-rc")

    // ExoPlayer
    api (libs.exoplayer.core)
    api ("com.google.android.exoplayer:exoplayer-ui:2.18.1")
    api ("com.google.android.exoplayer:extension-mediasession:2.18.1")

    // Material-Dialogs.
    implementation ("io.github.vanpra.compose-material-dialogs:core:0.8.1-rc")

    // CameraX
    implementation ("androidx.camera:camera-core:1.2.0")
    implementation ("androidx.camera:camera-lifecycle:1.2.0")
    implementation ("androidx.camera:camera-view:1.2.0")
    implementation ("androidx.camera:camera-extensions:1.2.0")

    //Timber.
    implementation ("com.jakewharton.timber:timber:5.0.1")

    //Swipe.
    implementation ("me.saket.swipe:swipe:1.0.0")

    //Phoenix.
    implementation ("com.jakewharton:process-phoenix:2.1.2")

    //Global Exception Handler.
    implementation ("com.github.emirhankolver:GlobalExceptionHandler:1.0.1")

    //Coil.
    implementation ("io.coil-kt:coil-compose:2.2.2")
    implementation("io.coil-kt:coil-svg:2.2.2")
    implementation ("io.coil-kt:coil-gif:2.2.2")

    //Glide.
    implementation ("com.github.bumptech.glide:compose:1.0.0-alpha.1")
    implementation ("com.github.bumptech.glide:glide:4.14.2")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.14.2")

    //

    //Test.
    testImplementation (libs.androidx.junit)
    debugImplementation (libs.compose.manifest)
    debugImplementation (libs.compose.uitest)
    androidTestImplementation (libs.androidx.extjunit)
    androidTestImplementation (libs.compose.junit4)
    androidTestImplementation(libs.bundles.composetest) {
        exclude(group = "androidx.core", module = "core-ktx")
        exclude(group = "androidx.customview", module = "customview")
        exclude(group = "androidx.activity", module = "activity")
        exclude(group = "androidx.lifecycle", module = "lifecycle-runtime-ktx")
    }
}