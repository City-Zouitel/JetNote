plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.composeVoyager)
    alias(libs.plugins.cityzouitel.androidCompose)
}

android {
    namespace = "city.zouitel.permissions"
}

dependencies {
    //
    api("dev.icerock.moko:permissions-compose:0.19.0")
    api("dev.icerock.moko:permissions-camera:0.19.0")
    api("dev.icerock.moko:permissions-gallery:0.19.0")
    api("dev.icerock.moko:permissions-microphone:0.19.0")
    api("dev.icerock.moko:permissions-notifications:0.19.0")
    api("dev.icerock.moko:permissions-storage:0.19.0")
}