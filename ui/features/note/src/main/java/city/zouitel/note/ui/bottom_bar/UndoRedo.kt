package city.zouitel.note.ui.bottom_bar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import city.zouitel.systemDesign.Cons.KEY_CLICK
import city.zouitel.systemDesign.DataStoreVM
import city.zouitel.systemDesign.Icons.REDO_ICON
import city.zouitel.systemDesign.Icons.UNDO_ICON
import city.zouitel.systemDesign.MaterialColors
import city.zouitel.systemDesign.MaterialColors.Companion.SURFACE_VARIANT
import city.zouitel.systemDesign.SoundEffect
import org.koin.androidx.compose.koinViewModel

// TODO: need improvement.
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UndoRedo(
    dataStoreVM: DataStoreVM = koinViewModel(),
    titleFieldState: MutableState<String?>,
    descriptionFieldState: MutableState<String?>,
    isTitleFieldSelected : MutableState<Boolean>,
    isDescriptionFieldSelected : MutableState<Boolean>,
) {
    val titleStack = remember { mutableStateListOf<String>() }
    val descriptionStack = remember { mutableStateListOf<String>() }

    val ctx = LocalContext.current
    val haptic = LocalHapticFeedback.current

    val thereIsSoundEffect = remember(dataStoreVM, dataStoreVM::getSound).collectAsState()

    val getMatColor = MaterialColors().getMaterialColor
    val sound = SoundEffect()

    Icon(
        painter = painterResource(id = UNDO_ICON),
        contentDescription = null,
        tint = contentColorFor(backgroundColor = getMatColor(SURFACE_VARIANT)),
        modifier = Modifier
            .size(20.dp)
            .combinedClickable(
                onLongClick = {
                    // To make vibration.
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                }
            ) {
                sound.makeSound(ctx, KEY_CLICK, thereIsSoundEffect.value)
                runCatching {
                    if (isTitleFieldSelected.value) {
                        if (titleFieldState.value?.isNotEmpty() == true) {
                            titleStack += titleFieldState.value!!
                                .split(' ')
                                .takeLast(1)
                        }
                        titleFieldState.value = if (!titleFieldState.value?.contains(' ')!!) {
                            ""
                        } else {
                            titleFieldState.value!!.substringBeforeLast(' ')
                        }

                    }
                    if (isDescriptionFieldSelected.value) {

                        if (descriptionFieldState.value?.isNotEmpty() == true) {
                            descriptionStack += descriptionFieldState.value!!
                                .split(' ')
                                .takeLast(1)
                        }
                        descriptionFieldState.value =
                            if (!descriptionFieldState.value?.contains(' ')!!) {
                                ""
                            } else {
                                descriptionFieldState.value!!.substringBeforeLast(' ')
                            }
                    }
                }
            }
    )

    Icon(
        painter = painterResource(id = REDO_ICON),
        contentDescription = null,
        tint = contentColorFor(backgroundColor = getMatColor(SURFACE_VARIANT)),
        modifier = Modifier
            .size(20.dp)
            .combinedClickable(
                onLongClick = {
                    // To make vibration.
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                }
            ) {
                sound.makeSound(ctx, KEY_CLICK, thereIsSoundEffect.value)
                runCatching {
                    if (isTitleFieldSelected.value) {
                        if (titleStack.isNotEmpty()) {
                            titleFieldState.value += " " + titleStack.last()
                            titleStack -= titleStack.last()
                        }
                    }
                    if (isDescriptionFieldSelected.value) {
                        if (descriptionStack.isNotEmpty()) {
                            descriptionFieldState.value += " " + descriptionStack.last()
                            descriptionStack -= descriptionStack.last()
                        }
                    }
                }
            }
    )
}
