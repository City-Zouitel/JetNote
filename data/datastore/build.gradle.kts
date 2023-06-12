plugins {
    id("city.android-lib")
    id("city.dagger-hilt")
}

android {
    namespace = "com.example.datastore"
}

dependencies {

    //DataStore.
    implementation (libs.datastore)

    //AndroidX.
    implementation(libs.androidx.constraintlayout)

    //SecurityDatastore.
    implementation("io.github.osipxd:security-crypto-datastore-preferences:1.0.0-alpha04")
}