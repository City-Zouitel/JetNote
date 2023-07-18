import com.example.source.Libraries.appcompat
import com.example.source.Libraries.core
import com.example.source.Libraries.coreKtx
import com.example.source.Libraries.runTimeKtx
import com.example.source.androidConfig
import com.example.source.proguardConfig
import gradle.kotlin.dsl.accessors._71f190358cebd46a469f2989484fd643.android
import gradle.kotlin.dsl.accessors._71f190358cebd46a469f2989484fd643.implementation

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








