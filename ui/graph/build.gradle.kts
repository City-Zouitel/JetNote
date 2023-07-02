plugins {
    id("city.android-lib")
    id("city.compose-lib")
    id("city.dagger-hilt")
}

android {
    namespace = "com.example.graph"
}

dependencies {
    //Projects.
    implementation(projects.data.datastore)
    implementation(projects.data.local)
    implementation(projects.domain)
    implementation(projects.service.notification)
    implementation(projects.common.commonUi)
    implementation(projects.ui.features.widget)
    implementation(projects.ui.features.tags)
    implementation(projects.ui.features.camera)
    implementation(projects.ui.features.record)
    implementation(projects.ui.features.tasks)
    implementation(projects.ui.features.mediaPlayer)
    implementation(projects.ui.features.reminder)
    implementation(projects.ui.features.note)
    implementation(projects.ui.features.quickNote)
    implementation(projects.ui.features.links)
    implementation(projects.api)

    //AndroidX.
    implementation(libs.androidx.constraintlayout)

    //LiveData.
    implementation(libs.livedatakts)

    //Lifecycle.
    implementation(libs.androidx.lifecycle.runtime.compose)

    //Accompanist.
    implementation(libs.accompanist.permissions)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.accompanist.navigation.animation)
    implementation(libs.accompanist.swiperefresh)
    implementation(libs.accompanist.flowlayout)
    implementation(libs.accompanist.insets)

    //Sketchbook.
    implementation (libs.sketchbook)
    //Balloon.
    implementation ("com.github.skydoves:balloon-compose:1.5.4")

    //Swipe.
    implementation (libs.swipe)

    //Coil.
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    //License.
    implementation (libs.aboutlibraries.comp)

    //Baha-UrlPreview.
    implementation(libs.url.preview)

    //Beetle.
    implementation(libs.beetle)
}