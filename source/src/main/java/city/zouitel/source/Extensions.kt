package city.zouitel.source

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

fun CommonExtension<*, *, *, *, *>.androidConfig(libs: VersionCatalog) {

    defaultConfig {
        compileSdk = 34 /*libs.findVersion("").get().requiredVersion.toInt()*/
        minSdk = 26 /*libs.findVersion("").get().requiredVersion.toInt()*/
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

}

fun CommonExtension<*, *, *, *,*>.composeConfig() {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
        /*libs.findVersion("").get().requiredVersion.toInt()*/
    }
}

private fun CommonExtension<*, *, *, *, *>.kotlinOptions (options: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", options)
}

fun LibraryExtension.proguardConfig() {
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

fun DependencyHandlerScope.androidDependencies(libs: VersionCatalog) {
    add("implementation", libs.findLibrary("core-ktx").get())
    add("implementation", libs.findLibrary("appcompat").get())
    add("implementation", libs.findLibrary("lifecycle-runtime-ktx").get())
}

fun DependencyHandlerScope.composeDependencies(libs: VersionCatalog) {
    add("implementation", platform(libs.findLibrary("compose-bom").get()))
    add("implementation", libs.findLibrary("activity-compose").get())
    add("implementation", libs.findLibrary("ui").get())
    add("implementation", libs.findLibrary("ui-graphics").get())
    add("implementation", libs.findLibrary("ui-tooling").get())
    add("implementation", libs.findLibrary("material3").get())
    add("implementation", libs.findLibrary("compose-navigation").get())
}

//fun DependencyHandlerScope.koinDependencies(libs: VersionCatalog) {
//    add("implementation", libs.findLibrary("koin-core").get())
//    add("implementation", libs.findLibrary("koin-android").get())
//    add("implementation", libs.findLibrary("koin-compose").get())
//    add("implementation", libs.findLibrary("koin-worker").get())
//}