plugins {
    id("city.android-lib")
    id("city.compose-lib")
    id("city.dagger-hilt")
}

android {
    namespace = "com.example.tags"
}

dependencies {
    //Projects.
    implementation(projects.common.commonUi)
    implementation(projects.domain)
    implementation(projects.data.datastore)

    //AndroidX.
    implementation(libs.androidx.constraintlayout)

    //LiveData.
    implementation(libs.livedatakts)

    //Lifecycle.
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    //Accompanist.
    implementation(libs.accompanist.flowlayout)
}