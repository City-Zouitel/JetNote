package com.example.mobile.initializer

import android.content.Context
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ProcessLifecycleInitializer
import com.rousetime.android_startup.AndroidStartup
import com.rousetime.android_startup.Startup

internal class ComposeInitializer: AndroidStartup<Unit>() {
    override fun callCreateOnMainThread(): Boolean = true

    override fun create(context: Context) {
        ComposeView(context)
    }

    override fun waitOnMainThread(): Boolean = false
}