package city.zouitel.source

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    common: CommonExtension<*, *, *, *, *>
) {
    common.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = "1.5.4" //libs.findVersion("").get().toString()
        }

        dependencies {
                add("implementation", platform(libs.findLibrary("compose-bom").get()))
                add("implementation", libs.findLibrary("activity-compose").get())
                add("implementation", libs.findLibrary("ui").get())
                add("implementation", libs.findLibrary("ui-graphics").get())
                add("implementation", libs.findLibrary("ui-tooling").get())
                add("implementation", libs.findLibrary("material3").get())
                add("implementation", libs.findLibrary("compose-navigation").get())
            }

        testOptions {
            unitTests {
                isIncludeAndroidResources = true
            }
        }
    }
}

