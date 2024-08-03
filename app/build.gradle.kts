@Suppress("DSL_SCOPE_VIOLATION")
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
        versionCode = libs.versions.code.v.get().toInt()
        versionName = libs.versions.name.v.get()
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
    implementation(projects.core.database)
    implementation(projects.core.datastore)
    implementation(projects.core.repository)
    implementation(projects.core.security)
    implementation(projects.domain)
    implementation(projects.init)
    implementation(projects.services.notifications)
    implementation(projects.services.shortcuts)
    implementation(projects.common.systemDesign)
    implementation(projects.common.logic)
    implementation(projects.root)
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