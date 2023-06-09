package com.example.source

import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.provider.Provider

object Libraries {

    //AndroidX.
    internal val VersionCatalog.core: Provider<MinimalExternalModuleDependency>
        get() = findLibrary("androidx_core").get()

    internal val VersionCatalog.coreKtx: Provider<MinimalExternalModuleDependency>
        get() = findLibrary("androidx_corektx").get()

    internal val VersionCatalog.appcompat: Provider<MinimalExternalModuleDependency>
        get() = findLibrary("androidx_appcompat").get()

    internal val VersionCatalog.runTimeKtx: Provider<MinimalExternalModuleDependency>
        get() = findLibrary("androidx_runtimektx").get()

    //Compose.
    internal val VersionCatalog.ui: Provider<MinimalExternalModuleDependency>
        get() = findLibrary("compose_ui").get()

    internal val VersionCatalog.toolingPreview: Provider<MinimalExternalModuleDependency>
        get() = findLibrary("compose_toolingpreview").get()

    internal val VersionCatalog.navigation: Provider<MinimalExternalModuleDependency>
        get() = findLibrary("compose_navigation").get()

    internal val VersionCatalog.viewmodel: Provider<MinimalExternalModuleDependency>
        get() = findLibrary("compose_viewmodel").get()

    internal val VersionCatalog.activity: Provider<MinimalExternalModuleDependency>
        get() = findLibrary("compose_activity").get()

    internal val VersionCatalog.material3: Provider<MinimalExternalModuleDependency>
        get() = findLibrary("compose_material3").get()

    internal val VersionCatalog.constraintLayout: Provider<MinimalExternalModuleDependency>
        get() = findLibrary("compose_constraintlayout").get()

    //Dagger-Hilt.
    internal val VersionCatalog.dagger: Provider<MinimalExternalModuleDependency>
        get() = findLibrary("dagger").get()

    internal val VersionCatalog.compiler: Provider<MinimalExternalModuleDependency>
        get() = findLibrary("dagger_compiler").get()

    internal val VersionCatalog.hilt: Provider<MinimalExternalModuleDependency>
        get() = findLibrary("dagger_hilt").get()

    internal val VersionCatalog.daggerHiltCompiler: Provider<MinimalExternalModuleDependency>
        get() = findLibrary("dagger_hiltcompiler").get()

    internal val VersionCatalog.hiltNavComp: Provider<MinimalExternalModuleDependency>
        get() = findLibrary("hilt_navcomp").get()

    internal val VersionCatalog.hiltCompiler: Provider<MinimalExternalModuleDependency>
        get() = findLibrary("hilt_compiler").get()

    internal val VersionCatalog.hiltWork: Provider<MinimalExternalModuleDependency>
        get() = findLibrary("hilt_work").get()

}