@file:Suppress("CONTEXT_RECEIVERS_DEPRECATED")

package city.zouitel.jetnote

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import city.zouitel.logic.asShortToast
import city.zouitel.screens.about_screen.AboutScreen
import city.zouitel.screens.main_screen.MainScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal interface IntentHandler: CoroutineScope {

    context (Context)
    @SuppressLint("NotConstructor")
    @Composable
    fun IntentHandler(
        intent: Intent,
        composer: (@Composable () -> Unit) -> Unit
    ) {
        val navigator = LocalNavigator.current

        intent.apply {
            if (action == Intent.ACTION_SEND && type == "text/plain") {
                getStringExtra(Intent.EXTRA_TEXT)?.let { title ->
                    launch {
                        title.asShortToast()
                    }
                }
            }
            if (action == Intent.ACTION_VIEW) {
                if (extras?.containsKey("new_note_shortcut") == true) {
                    getBooleanExtra("new_note_shortcut", false)
                    launch { navigator?.push(listOf(MainScreen(true), AboutScreen())) }
                }
                if (extras?.containsKey("quick_note") == true) {
                    getBooleanExtra("quick_note", false)
                    launch {
                        // TODO:
                    }
                }
                if (extras?.containsKey("new_record") == true) {
                    getBooleanExtra("new_record", false)
                    launch {
                        // TODO:
                    }
                }
            }
            removeCategory(Intent.CATEGORY_DEFAULT)
            action = null
        }
    }
}