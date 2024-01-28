@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.licenses)
//    id ("com.chrisney.enigma")
}

android {
    namespace = "city.zouitel.jetnote"
    compileSdk = 34

    defaultConfig {
        applicationId = "city.zouitel.jetnote"
        minSdk = 26
        targetSdk = 34
        versionCode = 400
        versionName = "4.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
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
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    lint {
        abortOnError = false
    }

    /**
     * Configure the build for multiple APKs
     */
    splits {

        /**
         * Configure multiple APKs for screen densities.
         */
        density {
            isEnable = true
            include("ldpi", "xxhdpi", "xxxhdpi")
            compatibleScreens("small", "normal", "large", "xlarge")
        }
        /**
         * Configure multiple APKs for Application Binary Interfaces.
         */
        abi {
            isEnable = true
            reset()
            include("x86", "x86_64", "armeabi-v7a", "arm64-v8a")
            isUniversalApk = false
        }
    }
}

//enigma.enabled = true
//enigma.injectFakeKeys = true

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.compose.navigation)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    //Modules.
    implementation(projects.core.database)
    implementation(projects.core.datastore)
    implementation(projects.core.repository)
    implementation(projects.domain)
    implementation(projects.init)
    implementation(projects.services.notifications)
    implementation(projects.ui.common.systemDesign)
    api(projects.ui.navigation)

    //Lifecycle.
    implementation(libs.lifecycle.compose.viewmodel)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.process)

    //WorkManager.
    implementation(libs.workmanager)

    //Accompanist.
    implementation(libs.accompanist.systemuicontroller)

    //Baha-UrlPreview.
    implementation(libs.url.preview)

    //Beetle.
    implementation(libs.beetle)

    //Global Exception.
//    implementation(libs.globalexception)

    //Koin.
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.worker)
    implementation(libs.koin.compose)
}