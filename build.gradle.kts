
plugins {
    alias(libs.plugins.dependencyanalysis)
    id("com.android.application") version "7.4.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("com.android.library") version "7.4.2" apply false
    id("com.android.dynamic-feature") version "7.4.2" apply false
}

buildscript {
    dependencies {
        classpath(libs.dagger.hilt.gradle.plugin)
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
