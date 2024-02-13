@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.androidApplication)
    alias(libs.plugins.licenses)
//    id ("com.chrisney.enigma")
}

android {
    namespace = "city.zouitel.jetnote"
    defaultConfig {
        applicationId = "city.zouitel.jetnote"
        versionCode = libs.versions.codeVersion.get().toInt()
        versionName = libs.versions.nameVersion.get()
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
}

//enigma.enabled = true
//enigma.injectFakeKeys = true

dependencies {
    //Modules.
    implementation(projects.core.database)
    implementation(projects.core.datastore)
    implementation(projects.core.repository)
    implementation(projects.domain)
    implementation(projects.init)
    implementation(projects.services.notifications)
    implementation(projects.services.shortcuts)
    implementation(projects.ui.common.systemDesign)
    implementation(projects.ui.common.logic)
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
    implementation(libs.koin.worker)
}