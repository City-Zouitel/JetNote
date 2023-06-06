plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.android.gradle.plugin)
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    google()
    gradlePluginPortal()
}
