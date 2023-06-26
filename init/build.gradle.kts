plugins {
    id("city.android-lib")
    id("city.dagger-hilt")
}

android {
    namespace = "city.zouitel.init"
}

dependencies {
    implementation(libs.androidx.startup)
    //Lifecycle.
    implementation(libs.androidx.lifecycle.process)
    //Sqlcipher.
    implementation (libs.sqlcipher)
    implementation("androidx.sqlite:sqlite:2.3.1")
}