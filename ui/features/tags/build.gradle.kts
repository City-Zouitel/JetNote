@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.androidCompose)
    alias(libs.plugins.cityzouitel.composeVoyager)
}

android {
    namespace = "city.zouitel.tags"
}

dependencies {

    //Modules.
    implementation(projects.domain)
    implementation(projects.common.systemDesign)
    implementation(projects.core.local.datastore)

    //AndroidX.
    implementation(libs.constraintlayout)

    //LiveData.
    implementation(libs.lifecycle.livedata)

    //Lifecycle.
    implementation(libs.lifecycle.compose.viewmodel)
    implementation(project(":common:logic"))

    //Accompanist.
//    implementation(libs.accompanist.flowlayout)
}