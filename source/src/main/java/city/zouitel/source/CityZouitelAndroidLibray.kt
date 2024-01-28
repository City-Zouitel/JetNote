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
        compileSdk = 34

        defaultConfig {
            minSdk = 26
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

//        android {
//            buildTypes {
//                release {
//                    isShrinkResources = true
//                    isMinifyEnabled = true
//                    proguardFiles(
//                        getDefaultProguardFile("proguard-android-optimize.txt"),
//                        "proguard-rules.pro"
//                    )
//                }
//            }
//        }

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        kotlinOptions {
            jvmTarget = "1.8"
        }

        dependencies {
            androidDependencies(libs)
            androidTestDependencies(libs)
        }
    }
}

private fun CommonExtension<*, *, *, *, *>.kotlinOptions (options: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", options)
}