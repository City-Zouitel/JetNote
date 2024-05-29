package city.zouitel.jetnote

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import city.zouitel.logic.asShortToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal interface IntentHandler: CoroutineScope {

    context (Context)
    @SuppressLint("NotConstructor")
    @Composable
    fun IntentHandler(
        intent: Intent,
        navigator: Navigator?,
        composer: (@Composable () -> Unit) -> Unit
    ) {
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
                    launch {
                        "new_note_shortcut".asShortToast()
                    }
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