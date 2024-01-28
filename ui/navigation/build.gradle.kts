@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.licenses)
}

android {
    namespace = "city.zouitel.navigation"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //Projects.
    implementation(projects.core.datastore)
    implementation(projects.core.database)
    implementation(projects.domain)
    implementation(projects.services.notifications)
    implementation(projects.ui.common.systemDesign)
    api(projects.ui.features.tags)
    api(projects.ui.features.camera)
    api(projects.ui.features.recorder)
    api(projects.ui.features.tasks)
    api(projects.ui.features.audios)
    api(projects.ui.features.reminder)
    api(projects.ui.features.note)
    api(projects.ui.features.quickNote)
    api(projects.ui.features.links)
    api(projects.ui.features.widget)

    //AndroidX.
    implementation(libs.constraintlayout)

    //LiveData.
    implementation(libs.lifecycle.livedata)

    //Accompanist.
    implementation(libs.accompanist.permissions)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.accompanist.navigation.animation)
    implementation(libs.accompanist.swiperefresh)
    implementation(libs.accompanist.flowlayout)
    implementation(libs.accompanist.insets)

    //Sketchbook.
    implementation (libs.sketchbook)
    //Balloon.
    implementation ("com.github.skydoves:balloon-compose:1.5.3")

    //Swipe.
    implementation (libs.swipe)

    //Coil.
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    //License.
    implementation (libs.licenses)

    //Baha-UrlPreview.
    implementation(libs.url.preview)

    //Beetle.
    implementation(libs.beetle)

    //Koin.
    implementation(libs.koin.core)
    implementation(libs.koin.compose)
}