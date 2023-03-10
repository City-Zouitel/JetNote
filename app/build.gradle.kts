plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    id ("com.mikepenz.aboutlibraries.plugin")
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.mobile"
    compileSdk = 33

    defaultConfig {
        applicationId = "city.zouitel.jetnote"
        minSdk = 25
        targetSdk = 33
        versionCode = 201
        versionName = "2.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        compileSdkPreview ="UpsideDownCake"
    }

    buildTypes {
        release {
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
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

hilt {
    enableAggregatingTask = true
}

dependencies {

    implementation(projects.data.datastore)
    implementation(projects.data.local)

    implementation(projects.domain)

    implementation(projects.common.notification)
    implementation(projects.common.commonUi)
//    implementation(projects.ui.graph)
    implementation(projects.ui.features.glance)
    implementation(projects.ui.features.tags)
    implementation(projects.ui.features.camera)
    implementation(projects.ui.features.record)
    implementation(projects.ui.features.tasks)
    implementation(projects.ui.features.mediaPlayer)
    implementation(projects.ui.features.reminder)
    implementation(projects.ui.features.note)
    implementation(projects.ui.features.quickNote)

    //AndroidX.
    implementation(libs.androidx.core)
    implementation(libs.androidx.corektx)
    implementation ("androidx.core:core-ktx:+")
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

//        //Room.
//    implementation(libs.roomkts)
//    api(libs.room.runtime)
//    ksp(libs.room.compiler)

    //DataStore.
//    implementation (libs.datastore)

    //LiveData.
    implementation(libs.livedatakts)

    //Dagger-Hilt
    implementation (libs.dagger)
    implementation (libs.dagger.hilt)
    implementation (libs.hilt.navcomp)
    kapt (libs.dagger.compiler)
    kapt (libs.hilt.compiler)
    kapt (libs.dagger.hiltcompiler)

//    //Accompanist.
    implementation(libs.accompanist.permissions)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.accompanist.navigation.animation)
    implementation(libs.accompanist.swiperefresh)
    implementation(libs.accompanist.flowlayout)
    implementation(libs.accompanist.insets)

//    // ExoPlayer
//    api(libs.exoplayer.core)
//    api(libs.exoplayer.ui)
//    api(libs.exoplayer.extension)
//
//    // CameraX
//    implementation (libs.camerax.core)
//    implementation (libs.camerax.lifecycle)
//    implementation (libs.camerax.view)
//    implementation (libs.camerax.extensions)
//
//    //Sketchbook.
    implementation (libs.sketchbook)
//
//    //Swipe.
    implementation (libs.swipe)

    //Global Exception Handler.
    implementation (libs.globalexception)

//    //Glide.
    implementation (libs.glide)
    implementation (libs.glide.comp)
    kapt (libs.glide.compiler)

//    //License.
    implementation (libs.aboutlibraries.comp)

//    //Retrofit.
//    implementation(libs.retrofit)
//
//    //Okhttp.
//    implementation(libs.okhttp.bom)
//    implementation(libs.okhttp.bom.core)
//    implementation(libs.okhttp.bom.interceptor)

    //Serialization.
//    implementation(libs.kotlinx.serialization)

    //Baha-UrlPreview.
    implementation(libs.url.preview)

    //Beetle.
    implementation(libs.beetle)

    //
//    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.10")
//    debugImplementation ("com.guolindev.glance:glance:1.1.0")

    //Test.
    testImplementation (libs.androidx.junit)
    debugImplementation (libs.compose.manifest)
    debugImplementation (libs.compose.uitest)

    androidTestImplementation (libs.androidx.extjunit)
    androidTestImplementation (libs.compose.junit4)

    androidTestImplementation(libs.bundles.composetest) {
        exclude(group = "androidx.core", module = "core-ktx")
        exclude(group = "androidx.activity", module = "activity")
        exclude(group = "androidx.lifecycle", module = "lifecycle-runtime-ktx")
    }
}