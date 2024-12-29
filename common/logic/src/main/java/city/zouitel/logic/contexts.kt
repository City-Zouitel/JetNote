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
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

context (Context)
/**
 * Plays a sound effect if the Boolean value is true.
 *
 * This infix function extends the [Boolean] class and provides a convenient way to conditionally
 * play a sound effect using the system's [AudioManager].
 *
 * @receiver The [Boolean] value that determines whether the sound effect will be played.
 *           If `true`, the sound effect will be played; otherwise, no action is taken.
 * @param audioManager The sound effect ID from [AudioManager]. For example, [AudioManager.FX_KEYPRESS_STANDARD].
 * @throws ClassCastException If the system's audio service cannot be cast to [AudioManager].
 * @throws SecurityException If the calling application is not allowed to play sound effects.
 */
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

context(ScreenModel)
    /**
     * Converts a [Flow] into a "logic flow" that behaves like a shared, hot flow with an initial value and a startup action.
     *
     * This function takes an existing [Flow] and transforms it into a state-backed flow that:
     *  - Shares its emissions among multiple collectors.
     *  - Retains the latest emitted value as its state.
     *  - Starts collecting upstream only when there are active collectors (hot).
     *  - Replays the last emitted value to new collectors.
     *  - Executes a specified [onStart] action when the flow starts being collected.
     *  - Keeps collecting for 5 seconds after the last collector disappears before stopping.
     *
     * This is particularly useful for representing the state of UI logic or other business logic that needs to be shared across
     * multiple observers (e.g., UI components) and needs an initial value.
     *
     * @param T The type of the items emitted by the flow.
     * @param initialValue The initial value of the state. This value will be emitted immediately to new collectors.
     * @param onStart A lambda function that will be executed when the flow starts being collected. This is useful for
     *                performing setup or triggering initial actions.
     * @return A [Flow] that shares its emissions, retains the latest value, starts lazily, and executes the [onStart] action.
     * @receiver The [Flow] to be converted into a logic flow.
     */
    fun <T> Flow<T>.asLogicFlow(initialValue: T, onStart: () -> Unit): StateFlow<T> {
        return this@asLogicFlow.onStart { onStart.invoke() }.stateIn(
            scope = screenModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = initialValue
        )
}
