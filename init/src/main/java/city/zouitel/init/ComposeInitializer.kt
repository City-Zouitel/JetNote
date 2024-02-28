package city.zouitel.init

import android.content.Context
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ProcessLifecycleInitializer
import androidx.startup.Initializer
import com.rousetime.android_startup.AndroidStartup

//class ComposeInitializer: Initializer<Unit> {
//    override fun create(context: Context) {
//        ComposeView(context)
//    }
//
//    override fun dependencies(): MutableList<Class<ProcessLifecycleInitializer>> {
//        return mutableListOf(
//            ProcessLifecycleInitializer::class.java
//        )
//    }
//}

class ComposeInitializer: AndroidStartup<Unit>() {
    override fun callCreateOnMainThread(): Boolean {
        return true
    }

    override fun create(context: Context): Unit? {
        ComposeView(context)
        return null
    }

    override fun waitOnMainThread(): Boolean {
        return true
    }
}