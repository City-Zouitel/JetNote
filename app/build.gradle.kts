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
    implementation(projects.core.local.system)
    implementation(projects.core.network.networkRepo)
    implementation(projects.core.network.generativeai)
    implementation(projects.domain)
    implementation(projects.init)
    implementation(projects.services.notifications)
    implementation(projects.services.shortcuts)
    implementation(projects.services.playback)
    implementation(projects.services.tile)
    implementation(projects.common.systemDesign)
    implementation(projects.common.logic)
    implementation(projects.common.permissions)
    implementation(projects.security.rooted)
    implementation(projects.security.appLock)
    implementation(projects.security.screenshot)
    api(projects.ui.screens)
    implementation(projects.bin)

    //Lifecycle.
    implementation(libs.lifecycle.compose.viewmodel)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.process)

    //WorkManager.
    implementation(libs.workmanager)

    //Beetle.
    implementation(libs.beetle)

    //Koin.
    implementation(libs.koin.worker)

    //Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)

    //Media3.
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.session)
}