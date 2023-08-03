plugins {
    id("city.android-lib")
    id("city.dagger-hilt")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "city.zouitel.api"
}

dependencies {
    implementation(platform(libs.supabase.bom))
    implementation(libs.supabase.postgrest)
    implementation(libs.supabase.realtime)
    implementation(libs.supabase.gotrue)

    implementation(libs.ktor.cio)

    implementation(libs.kotlinx.serialization)

}