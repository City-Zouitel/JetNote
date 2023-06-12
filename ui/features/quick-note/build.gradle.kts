plugins {
    id("city.android-lib")
    id("city.compose-lib")
    id("city.dagger-hilt")
}

android {
    namespace = "com.example.quick_note"
}

dependencies {
    //Projects.
    implementation(projects.domain)
    implementation(projects.data.datastore)
    implementation(projects.ui.features.note)
    implementation(projects.common.commonUi)

    //AndroidX.
    implementation(libs.androidx.constraintlayout)

    //LiveData.
    implementation(libs.livedatakts)

    //Accompanist.
    implementation(libs.accompanist.systemuicontroller)
}