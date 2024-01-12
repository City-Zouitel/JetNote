import com.example.plugins.Libraries.appcompat
import com.example.plugins.Libraries.core
import com.example.plugins.Libraries.coreKtx
import com.example.plugins.Libraries.runTimeKtx
import com.example.plugins.androidConfig
import com.example.plugins.proguardConfig

plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    androidConfig()
    proguardConfig()
}

val libs: VersionCatalog = extensions
        .getByType<VersionCatalogsExtension>()
        .named("libs")

dependencies {

    //AndroidX.
    "implementation"(libs.core)
    "implementation"(libs.coreKtx)
    "implementation"(libs.appcompat)
    "implementation"(libs.runTimeKtx)
}








