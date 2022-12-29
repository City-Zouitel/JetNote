
buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.42")
        classpath ("com.google.gms:google-services:4.3.14")
    }

    repositories {
        mavenCentral()
        maven("https://jitpack.io")
        google()
    }

}

plugins {
    id("com.android.application") version "7.3.0" apply false
    id("com.android.library") version "7.3.0" apply false
    id ("org.jetbrains.kotlin.android") version "1.7.0" apply false
    id("com.android.dynamic-feature") version "7.3.0" apply false
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}
