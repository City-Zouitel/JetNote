package city.zouitel.jetnote

import android.annotation.SuppressLint
import android.os.Build
import android.view.Window
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
@SuppressLint("WrongConstant")
internal fun HideSystemUI(window: Window) {
    val view = LocalView.current
    val controller = WindowCompat.getInsetsController(window, view)

    controller.apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            hide(WindowInsets.Type.systemBars())
            systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}

internal fun binInit() {

}

    /*
    fun void() {
        mapOf(
            "Coffee" to "Prepare hot coffee for my self.",
            "Certification" to "Call instructor for complete details.",
            "Team Meeting" to "Planning sprint log for next product application updateById.",
            "Birthday Party" to "Tomorrow is my brother birthday there will be party at 7:00 pm.",
            "Vacation Tickets" to "Buy tickets for the family vacation.",
            "Appointment" to "Health check up with physician."

        ).forEach {
            viewmodel.value.addNote(
                Note(
                    uid = UUID.randomUUID().toString(),
                    title = it.key,
                    description = it.value
                )
            )
        }
    }
    */