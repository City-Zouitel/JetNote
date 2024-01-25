package city.zouitel.source

import gradle.kotlin.dsl.accessors._a313ba380b190e27ff4471c793b5aeae.android

val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    androidConfig(libs)
    proguardConfig()
}

dependencies {
    androidDependencies(libs)
    koinDependencies(libs)
}