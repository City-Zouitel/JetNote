plugins {
    id("city.android-lib")
    id("city.compose-lib")
    id("city.dagger-hilt")
}

android {
    namespace = "com.example.common_ui"
}

dependencies {
    implementation(projects.data.datastore)
    
    //AndroidX.
    implementation(libs.androidx.constraintlayout)

    //Accompanist.
    implementation(libs.accompanist.permissions)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.accompanist.navigation.animation)
    implementation(libs.accompanist.swiperefresh)
    implementation(libs.accompanist.flowlayout)

    //Baha-UrlPreview.
    implementation(libs.url.preview)

    //Balloon.
    implementation ("com.github.skydoves:balloon-compose:1.5.4")
}