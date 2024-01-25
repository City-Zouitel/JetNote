@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

dependencies {
    "compileOnly"(libs.kotlin.gradle.plugin)
    "compileOnly"(libs.android.gradle.plugin)
}

repositories {
    mavenCentral()
    google()
}
