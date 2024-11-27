plugins {
    alias(libs.plugins.cityzouitel.androidApplication)
    alias(libs.plugins.ksp)
    alias(libs.plugins.licenses)
    alias(libs.plugins.enigma)
}

android {
    namespace = "city.zouitel.jetnote"
    defaultConfig {
        applicationId = "city.zouitel.jetnote"
        versionCode = libs.versions.code.version.get().toInt()
        versionName = libs.versions.name.version.get()
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

enigma.enabled = true
enigma.injectFakeKeys = true

dependencies {
    //Modules.
    implementation(projects.core.local.database)
    implementation(projects.core.local.datastore)
    implementation(projects.core.local.repository)
    implementation(projects.core.local.base)
    implementation(projects.core.network.repository)
    implementation(projects.core.network.generativeai)
    implementation(projects.domain)
    implementation(projects.init)
    implementation(projects.services.notifications)
    implementation(projects.services.shortcuts)
    implementation(projects.common.systemDesign)
    implementation(projects.common.logic)
    implementation(projects.security.rooted)
    implementation(projects.security.appLock)
    implementation(projects.security.screenshot)
    api(projects.ui.screens)

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

    //Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)
}