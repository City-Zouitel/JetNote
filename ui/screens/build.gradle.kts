@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.androidCompose)
    alias(libs.plugins.cityzouitel.androidNavigation)
    alias(libs.plugins.ksp)
    alias(libs.plugins.licenses)
}

android {
    namespace = "city.zouitel.screens"
}

dependencies {
    //Modules.
    implementation(projects.core.datastore)
    implementation(projects.core.database)
    implementation(projects.domain)
    implementation(projects.services.notifications)
    implementation(projects.common.systemDesign)
    implementation(projects.common.logic)
    api(projects.ui.features.tags)
    api(projects.ui.features.camera)
    api(projects.ui.features.recorder)
    api(projects.ui.features.tasks)
    api(projects.ui.features.audios)
    api(projects.ui.features.reminder)
    api(projects.ui.features.note)
    api(projects.ui.features.quickNote)
    api(projects.ui.features.links)
    api(projects.ui.features.widget)
    api(projects.ui.features.media)
    implementation(projects.root)

    //AndroidX.
    implementation(libs.constraintlayout)

    //Compose.
    implementation(libs.compose.material)

    //LiveData.
    implementation(libs.lifecycle.livedata)

    //Accompanist.
    implementation(libs.accompanist.permissions)
    implementation(libs.accompanist.systemuicontroller)
//    implementation(libs.accompanist.navigation.animation)
    implementation(libs.accompanist.swiperefresh)
//    implementation(libs.accompanist.flowlayout)
//    implementation(libs.accompanist.insets)

    //Sketchbook.
    implementation (libs.sketchbook)

    //Balloon.
    implementation ("com.github.skydoves:balloon-compose:1.6.4")

    //Swipe.
    implementation (libs.swipe)

    //Coil.
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    //License.
    implementation (libs.licenses)

    //Baha-UrlPreview.
    implementation(libs.url.preview)

    //Beetle.
    implementation(libs.beetle)
}