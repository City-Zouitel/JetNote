plugins {
    id("city.android-lib")
    id("city.dagger-hilt")
}

android {
    namespace = "city.zouitel.network"
}


dependencies {
    //Dagger-Hilt
    implementation (libs.dagger)
    implementation (libs.dagger.hilt)
    implementation (libs.hilt.work)
    kapt (libs.dagger.compiler)
    kapt (libs.hilt.compiler)
    kapt (libs.dagger.hiltcompiler)
}