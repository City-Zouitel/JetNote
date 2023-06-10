package com.example.source

import com.example.source.Libraries.activity
import com.example.source.Libraries.constraintLayout
import com.example.source.Libraries.material3
import com.example.source.Libraries.navigation
import com.example.source.Libraries.toolingPreview
import com.example.source.Libraries.ui
import com.example.source.Libraries.viewmodel
import gradle.kotlin.dsl.accessors._71f190358cebd46a469f2989484fd643.android
import gradle.kotlin.dsl.accessors._71f190358cebd46a469f2989484fd643.implementation
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType


plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    composeConfig()
}

val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    "implementation"(libs.ui)
    "implementation"(libs.material3)
    "implementation"(libs.navigation)
    "implementation"(libs.viewmodel)
    "implementation"(libs.toolingPreview)
    "implementation"(libs.activity)
    "implementation"(libs.constraintLayout)
}