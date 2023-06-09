package com.example.source

import org.gradle.api.artifacts.ExternalModuleDependencyBundle
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.provider.Provider

object Bundles {

    internal val VersionCatalog.compose: Provider<ExternalModuleDependencyBundle>
        get() = findBundle("compose").get()

    internal val VersionCatalog.composeTest: Provider<ExternalModuleDependencyBundle>
        get() = findBundle("composetest").get()
}