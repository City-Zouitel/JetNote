package city.zouitel.source

import org.gradle.api.artifacts.VersionCatalog

internal val VersionCatalog.compileSdk
    get() = findVersion("compileSdk-version").get().requiredVersion.toInt()

internal val VersionCatalog.minSdk
    get() = findVersion("minSdk-version").get().requiredVersion.toInt()
