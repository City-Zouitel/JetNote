














buildscript {
//    ext {
//      compose_version = "1.2.0"
//      val kotlin_version = "1.7.0"
//      val exoplayer_version = "2.18.1"
//      val room_version = "2.4.3"
//      val accompanist_version = "0.24.11-rc"
//      val dagger_hilt_version = "2.42"
//      val camerax_version = "1.1.0-beta01" // 1.2.0-beta01
//    }
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.42")
        classpath ("com.google.gms:google-services:4.3.14")
    }
    repositories {
        mavenCentral()
        maven("https://jitpack.io")
        google()
    }

}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.3.0" apply false
    id("com.android.library") version "7.3.0" apply false
    id ("org.jetbrains.kotlin.android") version "1.7.0" apply false
}

//task clean(type: Delete) {
//    delete rootProject.buildDir
//}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}
