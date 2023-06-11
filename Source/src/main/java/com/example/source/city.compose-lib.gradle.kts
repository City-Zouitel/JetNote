import com.example.source.Bundles.compose
import com.example.source.Bundles.composeTest
import com.example.source.Libraries.activity
import com.example.source.Libraries.constraintLayout
import com.example.source.Libraries.material3
import com.example.source.Libraries.navigation
import com.example.source.Libraries.toolingPreview
import com.example.source.Libraries.ui
import com.example.source.Libraries.viewmodel
import com.example.source.androidConfig
import com.example.source.composeConfig
import com.example.source.proguardConfig
import gradle.kotlin.dsl.accessors._71f190358cebd46a469f2989484fd643.android
import gradle.kotlin.dsl.accessors._71f190358cebd46a469f2989484fd643.implementation
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    composeConfig()
    androidConfig()
    proguardConfig()
}

val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    //Compose Bundle.
    "implementation"(libs.compose)
    "implementation"(libs.composeTest)

    //Compose Libs.
    "implementation"(libs.ui)
    "implementation"(libs.material3)
    "implementation"(libs.navigation)
    "implementation"(libs.viewmodel)
    "implementation"(libs.toolingPreview)
    "implementation"(libs.activity)
    "implementation"(libs.constraintLayout)
}