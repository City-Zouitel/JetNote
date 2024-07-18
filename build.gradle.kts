@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.dependencyanalysis) apply false
    alias(libs.plugins.licenses) apply false
    alias(libs.plugins.enigma) apply false
    id("com.guardsquare.appsweep") version "latest.release"
}

buildscript {
    dependencies {
        classpath(libs.enigma)
    }

    repositories {
        mavenCentral()
        maven("https://jitpack.io")
        google()
        gradlePluginPortal()
    }
}

tasks.register("clean", Delete::class){
    delete(rootProject.buildDir)
}