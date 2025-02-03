@file:Suppress("CONTEXT_RECEIVERS_DEPRECATED")

package city.zouitel.jetnote

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import city.zouitel.domain.provider.SharedScreen
import city.zouitel.logic.asShortToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

internal interface IntentHandler: CoroutineScope {

    context (Context)
    @OptIn(ExperimentalUuidApi::class)
    @SuppressLint("NotConstructor")
    @Composable
    fun IntentHandler(
        intent: Intent,
        composer: (@Composable () -> Unit) -> Unit
    ) {
        val navigator = LocalNavigator.current
        val workPlace = rememberScreen(SharedScreen.Workplace(
            Uuid.random().toString(),
            true,
            null,
            null,
            MaterialTheme.colorScheme.surface.toArgb(),
            MaterialTheme.colorScheme.onSurface.toArgb(),
            0
        ))

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
                    launch { navigator?.push(workPlace) }
                }
//                if (extras?.containsKey("quick_note") == true) {
//                    getBooleanExtra("quick_note", false)
//                    launch {
//                        // TODO:
//                    }
//                }
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