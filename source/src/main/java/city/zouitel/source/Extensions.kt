package city.zouitel.source

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

fun DependencyHandlerScope.androidDependencies(libs: VersionCatalog) {
    add("implementation", libs.findLibrary("core-ktx").get())
    add("implementation", libs.findLibrary("appcompat").get())
    add("implementation", libs.findLibrary("lifecycle-runtime-ktx").get())
}

fun DependencyHandlerScope.androidTestDependencies(libs: VersionCatalog) {
    add("testImplementation", libs.findLibrary("junit").get())
    add("androidTestImplementation", libs.findLibrary("androidx-test-ext-junit").get())
    add("androidTestImplementation", libs.findLibrary("espresso-core").get())
}

fun DependencyHandlerScope.androidComposeDependencies(libs: VersionCatalog) {
    add("implementation", platform(libs.findLibrary("compose-bom").get()))
    add("implementation", libs.findLibrary("activity-compose").get())
    add("implementation", libs.findLibrary("ui").get())
    add("implementation", libs.findLibrary("ui-graphics").get())
    add("implementation", libs.findLibrary("ui-tooling").get())
    add("implementation", libs.findLibrary("material3").get())
    add("implementation", libs.findLibrary("compose-navigation").get())
    add("implementation", libs.findLibrary("compose-constraintlayout").get())
}

fun DependencyHandlerScope.androidKoinDependencies(libs: VersionCatalog) {
    add("implementation", libs.findLibrary("koin-core").get())
    add("implementation", libs.findLibrary("koin-android").get())
}

fun DependencyHandlerScope.androidComposeKoinDependencies(libs: VersionCatalog) {
    add("implementation", libs.findLibrary("koin-compose").get())
}