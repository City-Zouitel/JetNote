
plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

dependencies {
    implementation(libs.kotlin)
    implementation(libs.android.gradle.plugin)
    implementation(libs.dagger.hilt.gradle.plugin)
}

repositories {
    mavenCentral()
    google()
}
