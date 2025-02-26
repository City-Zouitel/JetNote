package city.zouitel.note.providers

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.MutableState
import cafe.adriel.voyager.core.registry.ScreenProvider

data class Options(
    val uid: String,
    val titleState: TextFieldState?,
    val descriptionState: TextFieldState?,
    val priorityState: MutableState<Int>
): ScreenProvider
