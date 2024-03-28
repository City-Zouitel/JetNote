package city.zouitel.note.ui.bottom_bar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import city.zouitel.systemDesign.Cons.KEY_CLICK
import city.zouitel.systemDesign.DataStoreVM
import city.zouitel.systemDesign.Icons.REDO_ICON
import city.zouitel.systemDesign.Icons.UNDO_ICON
import city.zouitel.systemDesign.SoundEffect
import org.koin.androidx.compose.koinViewModel

// TODO: need improvement.
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UndoRedo(
    dataStoreVM: DataStoreVM = koinViewModel(),
    titleState: Pair<TextFieldState?, Boolean>,
    descriptionState: TextFieldState?,
) {
    val context = LocalContext.current
    val thereIsSoundEffect = remember(dataStoreVM, dataStoreVM::getSound).collectAsState()
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
            tint = contentColorFor(backgroundColor = MaterialTheme.colorScheme.surfaceVariant),
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
            tint = contentColorFor(backgroundColor = MaterialTheme.colorScheme.surfaceVariant),
        )
    }
}
