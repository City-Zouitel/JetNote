package city.zouitel.source

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.configureAndroidKoin() {
    dependencies {
        androidKoinDependencies(libs)
    }
}