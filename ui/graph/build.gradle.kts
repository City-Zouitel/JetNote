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
        targetSdk = 33

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
    }
}

dependencies {


//Projects.
    //
    implementation(projects.commonUi)

    implementation(projects.ui.tags)
    implementation(projects.ui.camera)
    implementation(projects.ui.record)
    implementation(projects.ui.tasks)
    implementation(projects.ui.mediaPlayer)
    implementation(projects.ui.reminder)
    implementation(projects.ui.note)
    //
    implementation(projects.notification)
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