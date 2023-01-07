
plugins {
    id("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    id ("com.mikepenz.aboutlibraries.plugin")
}

android {
    namespace = "com.example.jetnote"
    compileSdk = 33

    defaultConfig {
        applicationId = "city.zouitel.jetnote"
        minSdk = 25
        targetSdk = 33
        versionCode = 163
        versionName = "1.6.3"

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
    implementation (libs.roomkts)
    implementation (libs.room.runtime)
    annotationProcessor (libs.room.compiler)
    kapt (libs.room.compiler)

    //DataStore.
    implementation (libs.datastore)

    //LiveData.
    implementation (libs.livedatakts)

    //Dagger-Hilt
    implementation (libs.dagger)
    annotationProcessor (libs.dagger.compiler)
    implementation (libs.dagger.hilt)
    implementation (libs.hilt.navcomp)
    kapt (libs.hilt.compiler)
    kapt (libs.dagger.hiltcompiler)

    //Accompanist.
    implementation (libs.accompanist.permissions)
    implementation (libs.accompanist.pager)
    implementation (libs.accompanist.pager.indicators)
    implementation (libs.accompanist.systemuicontroller)
    implementation (libs.accompanist.navigation.animation)
    implementation (libs.accompanist.swiperefresh)
    implementation (libs.accompanist.flowlayout)

    // ExoPlayer
    api (libs.exoplayer.core)
    api (libs.exoplayer.ui)
    api (libs.exoplayer.extension)

    // CameraX
    implementation (libs.camerax.core)
    implementation (libs.camerax.lifecycle)
    implementation (libs.camerax.view)
    implementation (libs.camerax.extensions)

    //Sketchbook.
    implementation (libs.sketchbook)

    //Swipe.
    implementation (libs.swipe)

    //Global Exception Handler.
    implementation (libs.globalexception)

    //Glide.
    implementation (libs.glide)
    implementation (libs.glide.comp)
    annotationProcessor (libs.glide.compiler)

    //License.
    implementation (libs.aboutlibraries.comp)

    //
//    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.10")

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