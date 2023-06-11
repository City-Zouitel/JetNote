plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    id ("com.chrisney.enigma")
}

android {
    namespace = "com.example.mobile"
    compileSdk = 33

    defaultConfig {
        applicationId = "city.zouitel.jetnote"
        minSdk = 25
        targetSdk = 33
        versionCode = 320
        versionName = "3.2.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
//        compileSdkPreview ="UpsideDownCake"
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
        create("benchmark") {
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
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

enigma.enabled = true
enigma.injectFakeKeys = true

dependencies {

    //Modules.
    implementation(projects.common.commonUi)
    implementation(projects.service.notification)
    implementation(projects.ui.graph)
    implementation(projects.ui.features.widget)
    implementation(projects.ui.features.quickNote)

    //AndroidX.
    implementation(libs.androidx.core)
    implementation(libs.androidx.corektx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.runtimektx)
    ////////////
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.startup)

    //Compose.
    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
    implementation(libs.compose.activity)
    implementation(libs.compose.navigation)
    implementation(libs.compose.toolingpreview)
    implementation(libs.compose.viewmodel)
    implementation(libs.compose.constraintlayout)

    //Dagger-Hilt
    implementation (libs.dagger)
    implementation (libs.dagger.hilt)
    implementation (libs.hilt.navcomp)
    implementation (libs.hilt.work)
    kapt (libs.dagger.compiler)
    kapt (libs.hilt.compiler)
    kapt (libs.dagger.hiltcompiler)
    
    //Lifecycle.
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.process)

    //WorkManager.
    implementation(libs.androidx.workmanager)

    //Accompanist.
    implementation(libs.accompanist.systemuicontroller)

    //Baha-UrlPreview.
    implementation(libs.url.preview)

    //Beetle.
    implementation(libs.beetle)

    //Global Exception.
    implementation(libs.globalexception)

    //Sqlcipher.
    implementation (libs.sqlcipher)
    implementation("androidx.sqlite:sqlite:2.3.1")

    //
    implementation("io.github.idisfkj:android-startup:1.1.0")
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