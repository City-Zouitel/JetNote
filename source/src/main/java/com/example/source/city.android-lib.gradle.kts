import com.example.source.Libraries.appcompat
import com.example.source.Libraries.core
import com.example.source.Libraries.coreKtx
import com.example.source.Libraries.runTimeKtx
import com.example.source.androidConfig
import com.example.source.proguardConfig

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








