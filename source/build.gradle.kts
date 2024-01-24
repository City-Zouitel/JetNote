@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

dependencies {
    "implementation"(libs.kotlin.gradle.plugin)
    "implementation"(libs.android.gradle.plugin)
}

repositories {
    mavenCentral()
    google()
}
