package city.zouitel.note.ui.bottom_bar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.clearText
import androidx.compose.foundation.text2.input.setTextAndSelectAll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
//    titleFieldState: MutableState<String?>,
//    descriptionFieldState: MutableState<String?>,
//    isTitleFieldSelected : MutableState<Boolean>,
//    isDescriptionFieldSelected : MutableState<Boolean>,
    titleState: Pair<TextFieldState?, Boolean>,
    descriptionState: TextFieldState?,
//    isTitleState: MutableState<Boolean>
) {
//    val titleStack = remember { mutableStateListOf<String>() }
//    val descriptionStack = remember { mutableStateListOf<String>() }

    val context = LocalContext.current
//    val haptic = LocalHapticFeedback.current

    val thereIsSoundEffect = remember(dataStoreVM, dataStoreVM::getSound).collectAsState()

    val getMatColor = MaterialColors().getMaterialColor
    val sound = SoundEffect()

    IconButton(
        modifier = Modifier.size(23.dp),
        onClick = {
            sound.makeSound(context, KEY_CLICK, thereIsSoundEffect.value)
            if (titleState.second) titleState.first?.undoState?.undo() else descriptionState?.undoState?.undo()
        },
        enabled = if (titleState.second) titleState.first?.undoState?.canUndo!! else descriptionState?.undoState?.canUndo!!
    ) {
        Icon(
            painter = painterResource(id = UNDO_ICON),
            contentDescription = null,
            tint = contentColorFor(backgroundColor = getMatColor(SURFACE_VARIANT)),
        )
    }

    IconButton(
        modifier = Modifier.size(23.dp),
        onClick = {
            sound.makeSound(context, KEY_CLICK, thereIsSoundEffect.value)
            if (titleState.second) titleState.first?.undoState?.redo() else descriptionState?.undoState?.redo()

        },
        enabled = if (titleState.second) titleState.first?.undoState?.canRedo!! else descriptionState?.undoState?.canRedo!!,
    ) {
        Icon(
            painter = painterResource(id = REDO_ICON),
            contentDescription = null,
            tint = contentColorFor(backgroundColor = getMatColor(SURFACE_VARIANT)),
        )
    }
}
