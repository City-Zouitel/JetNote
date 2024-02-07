@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.cityzouitel.androidLibrary)
    alias(libs.plugins.cityzouitel.androidCompose)
}

android {
    namespace = "city.zouitel.widget"
}

dependencies {
    //Modules.
    implementation(projects.domain)
    implementation(projects.ui.common.systemDesign)

    //AndroidX.
    implementation(libs.constraintlayout)
    implementation(libs.glance)
    implementation(project(":ui:features:note"))
    implementation(project(":ui:features:tags"))
    implementation(project(":ui:features:tasks"))
    implementation(project(":ui:features:links"))
}