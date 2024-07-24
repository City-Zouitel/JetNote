package city.zouitel.source

import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.DependencyHandlerScope

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

fun DependencyHandlerScope.androidUiTestDependencies(libs: VersionCatalog) {
    val bom = libs.findLibrary("compose-bom").get()
    add("androidTestImplementation", (platform(bom)))
    add("androidTestImplementation", (libs.findLibrary("ui-test-junit4").get()))
    add("debugImplementation", (libs.findLibrary("ui-tooling").get()))
    add("debugImplementation", (libs.findLibrary("ui-test-manifest").get()))
}

fun DependencyHandlerScope.androidComposeDependencies(libs: VersionCatalog) {
    add("implementation", platform(libs.findLibrary("compose-bom").get()))
    add("implementation", libs.findLibrary("activity-compose").get())
    add("implementation", libs.findLibrary("compose-ui").get())
    add("implementation", libs.findLibrary("ui-graphics").get())
    add("implementation", libs.findLibrary("ui-tooling").get())
    add("implementation", libs.findLibrary("material3").get())
//    add("implementation", libs.findLibrary("compose-navigation").get())
    add("implementation", libs.findLibrary("compose-constraintlayout").get())
    add("implementation", libs.findLibrary("compose-foundation").get())

    // TODO: add bundles.
}

fun DependencyHandlerScope.androidKoinDependencies(libs: VersionCatalog) {
    add("implementation", libs.findLibrary("koin-core").get())
    add("implementation", libs.findLibrary("koin-android").get())
}

fun DependencyHandlerScope.androidComposeKoinDependencies(libs: VersionCatalog) {
    add("implementation", libs.findLibrary("koin-compose").get())
}

fun DependencyHandlerScope.voyagerDependencies(libs: VersionCatalog) {
    add("implementation", libs.findLibrary("voyager-navigator").get())
    add("implementation", libs.findLibrary("voyager-screenModel").get())
    add("implementation", libs.findLibrary("voyager-transitions").get())
    add("implementation", libs.findLibrary("voyager-koin").get())
    add("implementation", libs.findLibrary("voyager-bottom-sheet").get())
}