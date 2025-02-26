package city.zouitel.source

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureComposeVoyager(common: CommonExtension<*, *, *, *, *, *>) {
    common.apply {
        dependencies {
            voyagerDependencies(libs)
        }
    }
}