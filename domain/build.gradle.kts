@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.android.library)
    alias(libs.plugins.cityzouitel.android.koin)
}

android {
    namespace = "city.zouitel.domain"
}

dependencies {
    //ExoPlayer
    api(libs.exoplayer.core)
    api(libs.exoplayer.ui)
    api(libs.exoplayer.extension)
}