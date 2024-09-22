package city.zouitel.init

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat

@RequiresApi(Build.VERSION_CODES.R)
class HideUiSystemBars(val context: Context) {
    init {
        behavior()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("WrongConstant")
    fun behavior() {
        val view = View(context)
        val window = (view as Activity).window
        val controller = WindowCompat.getInsetsController(window, view)

        controller.apply {
            hide(android.view.WindowInsets.Type.systemBars())
            systemBarsBehavior = android.view.WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}