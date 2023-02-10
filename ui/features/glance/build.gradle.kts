plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.glance"
    compileSdk = 33

    defaultConfig {
        minSdk = 25
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

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

    implementation("androidx.glance:glance-appwidget:1.0.0-alpha05")
    implementation ("com.google.android.glance.tools:appwidget-host:0.2.2")
    implementation ("com.google.android.glance.tools:appwidget-configuration:0.2.2")
}