package city.zouitel.source

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    common: CommonExtension<*, *, *, *, *, *>
) {
    common.apply {
        buildFeatures {
            compose = true
            buildConfig = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = libs.findVersion("compose-v").get().requiredVersion
        }

        dependencies {
            androidComposeDependencies(libs)
            androidComposeKoinDependencies(libs)
        }

        testOptions {
            unitTests {
                isIncludeAndroidResources = true
            }
        }
    }
}

