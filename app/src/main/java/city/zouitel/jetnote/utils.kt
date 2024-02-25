package city.zouitel.jetnote

import android.view.View
import android.view.Window
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

internal fun hideSystemUI(window: Window) {

    WindowCompat.setDecorFitsSystemWindows(window, false)
    WindowInsetsControllerCompat(window, View(window.context)).let { controller ->
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }

/*
    fun showSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(
            window,
            mainContainer
        ).show(WindowInsetsCompat.Type.systemBars())
    }
*/

}