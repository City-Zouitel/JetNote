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

    /*
    fun void() {
        mapOf(
            "Coffee" to "Prepare hot coffee for my self.",
            "Certification" to "Call instructor for complete details.",
            "Team Meeting" to "Planning sprint log for next product application update.",
            "Birthday Party" to "Tomorrow is my brother birthday there will be party at 7:00 pm.",
            "Vacation Tickets" to "Buy tickets for the family vacation.",
            "Appointment" to "Health check up with physician."

        ).forEach {
            viewmodel.value.addNote(
                Note(
                    id = UUID.randomUUID().toString(),
                    title = it.key,
                    description = it.value
                )
            )
        }
    }
    */