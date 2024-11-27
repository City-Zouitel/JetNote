@file:Suppress("CONTEXT_RECEIVERS_DEPRECATED")

package city.zouitel.logic

import android.content.Context
import android.media.AudioManager
import android.widget.Toast
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

context (Context)
infix fun Boolean.makeSoundFor(audioManager: Int) {
    val systemService = this@Context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    if (this) systemService.playSoundEffect(audioManager, 1.0f)
}

/**
 * Displays this string as a short toast message.
 *
 * This is an extension function on String, allowing you to easily display
 * the string as a toast message with a short duration.
 *
 * @receiver The string to be displayed as the toast message.
 */
context (Context)
fun String.asShortToast() {
    Toast.makeText(this@Context, this, Toast.LENGTH_SHORT).show()
}

/**
 * Displays this string as a long toast message.
 *
 * This is an extension function on String, allowing you to easily display
 * the string as a toast with a long duration.
 *
 * @receiver The string to be displayed in the toast.
 */
context (Context)
fun String.asLongToast()  {
    Toast.makeText(this@Context, this, Toast.LENGTH_LONG).show()
}

/**
 * Converts this [Flow] to a [StateFlow] using [stateIn] operator.
 *
 * This extension function is designed for use within a ScreenModel (or similar architecture)
 * and automatically uses the `screenModelScope` for collection. It also configures the
 * [stateIn] operator to keep the state for 5 seconds after the last subscriber is gone.
 *
 * @param initialValue The initial value of the state flow.
 * @return A [StateFlow] representing the latest value emitted by this flow.
 */
context(ScreenModel)
fun <T> Flow<T>.asLogicFlow(initialValue: T): StateFlow<T> {
    return stateIn(
        scope = screenModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = initialValue
    )
}
