@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.androidCompose)
    alias(libs.plugins.cityzouitel.androidNavigation)
}

android {
    namespace = "city.zouitel.quicknote"
}

dependencies {
    //Modules.
    implementation(projects.domain)
    implementation(projects.common.systemDesign)
    implementation(projects.common.logic)
    implementation(projects.core.datastore)
    implementation(projects.ui.features.note)
    implementation(projects.ui.features.links)

    //AndroidX.
    implementation(libs.constraintlayout)

    //LiveData.
    implementation(libs.lifecycle.livedata)

    //Accompanist.
    implementation(libs.accompanist.systemuicontroller)
}