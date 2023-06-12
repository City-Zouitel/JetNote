package com.example.mobile.initializer

import android.content.Context
import androidx.compose.ui.platform.ComposeView
import com.rousetime.android_startup.AndroidStartup

internal class ComposeInitializer: AndroidStartup<Unit>() {
    override fun callCreateOnMainThread(): Boolean = true

    //    override fun create(context: Context) {
//        ComposeView(context)
//    }
//
//    override fun dependencies(): MutableList<Class<ProcessLifecycleInitializer>> {
//        return mutableListOf(
//            ProcessLifecycleInitializer::class.java
//        )
//    }
    override fun create(context: Context) {
        ComposeView(context)
    }

    override fun waitOnMainThread(): Boolean = false
}