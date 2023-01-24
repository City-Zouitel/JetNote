
plugins {
    alias(libs.plugins.dependencyanalysis)
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
