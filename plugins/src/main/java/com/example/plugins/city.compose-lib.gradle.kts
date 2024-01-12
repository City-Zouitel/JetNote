import com.example.plugins.Bundles.compose
import com.example.plugins.Bundles.composeTest
import com.example.plugins.Libraries.activity
import com.example.plugins.Libraries.constraintLayout
import com.example.plugins.Libraries.material3
import com.example.plugins.Libraries.navigation
import com.example.plugins.Libraries.toolingPreview
import com.example.plugins.Libraries.ui
import com.example.plugins.Libraries.viewmodel
import com.example.plugins.androidConfig
import com.example.plugins.composeConfig
import com.example.plugins.proguardConfig
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    composeConfig()
}

val libs: VersionCatalog = extensions
        .getByType<VersionCatalogsExtension>()
        .named("libs")

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