import com.example.source.Libraries.compiler
import com.example.source.Libraries.dagger
import com.example.source.Libraries.daggerHiltCompiler
import com.example.source.Libraries.hilt
import com.example.source.Libraries.hiltCompiler
import com.example.source.Libraries.hiltNavComp
import com.example.source.Libraries.hiltWork
import gradle.kotlin.dsl.accessors._71f190358cebd46a469f2989484fd643.implementation

plugins {
    id ("dagger.hilt.android.plugin")
    id ("kotlin-kapt")
}

val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    implementation(libs.dagger)
    implementation(libs.compiler)
    implementation(libs.hilt)
    implementation(libs.daggerHiltCompiler)
    implementation(libs.hiltNavComp)
    implementation(libs.hiltCompiler)
    implementation(libs.hiltWork)
}
