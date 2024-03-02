@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

dependencies {
    "implementation"(libs.kotlin.gradle.plugin)
    "implementation"(libs.android.gradle.plugin)
}

repositories {
    mavenCentral()
    google()
}

gradlePlugin {
    plugins {
        register("android-library") {
            id = "city.zouitel.android.library"
            implementationClass = "AndroidLibrary"
        }
    }

    plugins {
        register("android-compose") {
            id = "city.zouitel.android.compose"
            implementationClass = "AndroidCompose"
        }
    }

    plugins {
        register("android-application") {
            id = "city.zouitel.android.application"
            implementationClass = "AndroidApplication"
        }
    }

    plugins {
        register("android-navigation") {
            id = "city.zouitel.android.navigation"
            implementationClass = "AndroidNavigation"
        }
    }
}