
plugins {
//    id("com.android.application") version "7.3.1" apply false
//    id("com.android.library") version "7.3.1" apply false
//    id ("org.jetbrains.kotlin.android") version "1.7.0" apply false
//    id("com.android.dynamic-feature") version "7.3.1" apply false
    alias(libs.plugins.dependencyanalysis)
}

buildscript {
    dependencies {
        classpath(libs.dagger.hilt.gradle.plugin)
//        classpath ("com.google.gms:google-services:4.3.15")
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.android.gradle.plugin)
        classpath(libs.aboutlibraries.plugin)
    }

    repositories {
        mavenCentral()
        maven("https://jitpack.io")
        google()
        gradlePluginPortal()
    }

}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}
