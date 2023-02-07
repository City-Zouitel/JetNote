plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    id ("com.mikepenz.aboutlibraries.plugin")
}

android {
    namespace = "com.example.graph"
    compileSdk = 33

    defaultConfig {
        minSdk = 25
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {


//Projects.
    //
    implementation(projects.common.commonUi)

    implementation(projects.ui.features.tags)
    implementation(projects.ui.features.camera)
    implementation(projects.ui.features.record)
    implementation(projects.ui.features.tasks)
    implementation(projects.ui.features.mediaPlayer)
    implementation(projects.ui.features.reminder)
    implementation(projects.ui.features.note)
    //
    implementation(projects.common.notification)
    //
    implementation(projects.domain)
    //Data Access.
    implementation(projects.data.local)
    implementation(projects.data.datastore)
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
    implementation (libs.dagger)
    implementation (libs.dagger.hilt)
    implementation (libs.hilt.navcomp)
    implementation("androidx.core:core-ktx:+")
    kapt (libs.dagger.compiler)
    kapt (libs.hilt.compiler)
    kapt (libs.dagger.hiltcompiler)
    //Accompanist.
    implementation(libs.accompanist.permissions)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.accompanist.navigation.animation)
    implementation(libs.accompanist.swiperefresh)
    implementation(libs.accompanist.flowlayout)
    //Glide.
    implementation (libs.glide)
    implementation (libs.glide.comp)
    kapt (libs.glide.compiler)
    //Swipe.
    implementation (libs.swipe)
    //Sketchbook.
    implementation (libs.sketchbook)
    //License.
    implementation (libs.aboutlibraries.comp)

    //Baha-UrlPreview.
    implementation(libs.url.preview)

    //Beetle.
    implementation(libs.beetle)
}