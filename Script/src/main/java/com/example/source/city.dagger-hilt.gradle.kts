import com.example.source.Libraries.compiler
import com.example.source.Libraries.dagger
import com.example.source.Libraries.daggerHiltCompiler
import com.example.source.Libraries.hilt
import com.example.source.Libraries.hiltCompiler
import com.example.source.Libraries.hiltNavComp
import com.example.source.Libraries.hiltWork
import dagger.hilt.android.plugin.HiltExtension
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

plugins {
    id ("dagger.hilt.android.plugin")
    id ("kotlin-kapt")
}

val libs: VersionCatalog = extensions
        .getByType<VersionCatalogsExtension>()
        .named("libs")

val hilt = extensions
        .getByType<HiltExtension>()

val kapt = extensions
        .getByType<KaptExtension>()

kapt.correctErrorTypes = true
hilt.enableAggregatingTask = true

dependencies {
    "implementation"(libs.dagger)
    "implementation"(libs.hilt)
    "implementation"(libs.hiltWork)
    "implementation"(libs.hiltNavComp)
    "kapt"(libs.daggerHiltCompiler)
    "kapt"(libs.hiltCompiler)
    "kapt"(libs.compiler)
}
