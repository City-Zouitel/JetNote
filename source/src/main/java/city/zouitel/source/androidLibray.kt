package city.zouitel.source

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

fun Project.configureAndroidLibrary(
    common: CommonExtension<*, *, *, *, *>,
) {
    common.apply {
        compileSdk = libs.findVersion("compileSdkVersion").get().requiredVersion.toInt()

        defaultConfig {
            minSdk = libs.findVersion("minSdkVersion").get().requiredVersion.toInt()
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        kotlinOptions {
            jvmTarget = "17"
        }

        dependencies {
            androidDependencies(libs)
            androidTestDependencies(libs)
            androidKoinDependencies(libs)
        }
    }
}

private fun CommonExtension<*, *, *, *, *>.kotlinOptions (options: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", options)
}