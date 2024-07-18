package city.zouitel.source

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidApplication(
    common: CommonExtension <*, *, *, *, *, *>
) {
    common.apply {
        defaultConfig {
            vectorDrawables {
                useSupportLibrary = true
            }
        }

        lint {
            abortOnError = false
        }

        /**
         * Configure the build for multiple APKs
         */
        splits {
            /**
             * Configure multiple APKs for screen densities.
             */
            density {
                isEnable = true
                include("ldpi", "xxhdpi", "xxxhdpi")
                compatibleScreens("small", "normal", "large", "xlarge")
            }
            /**
             * Configure multiple APKs for Application Binary Interfaces.
             */
            abi {
                isEnable = true
                reset()
                include("x86", "x86_64", "armeabi-v7a", "arm64-v8a")
                isUniversalApk = true
            }
        }

        dependencies {
            androidUiTestDependencies(libs)
        }
    }
}