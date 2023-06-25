plugins {
    id("city.android-lib")
    id("city.compose-lib")
    id("city.dagger-hilt")
}

android {
    namespace = "com.example.note"
}

dependencies {
    //Projects.
    implementation(projects.common.commonUi)
    implementation(projects.domain)
    implementation(projects.data.datastore)
    implementation(projects.ui.features.reminder)
    implementation(projects.ui.features.record)
    implementation(projects.ui.features.tasks)
    implementation(projects.ui.features.mediaPlayer)
    implementation(projects.ui.features.tags)
    implementation(projects.ui.features.camera)
    implementation(projects.ui.features.links)
    implementation(projects.api)

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

    //Sketchbook.
    implementation (libs.sketchbook)
    //Balloon.
    implementation ("com.github.skydoves:balloon-compose:1.5.3")

    //Swipe.
    implementation (libs.swipe)

    //Baha-UrlPreview.
    implementation(libs.url.preview)
}