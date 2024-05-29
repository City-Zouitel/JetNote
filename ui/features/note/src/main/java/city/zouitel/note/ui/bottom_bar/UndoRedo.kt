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
import city.zouitel.note.ui.workplace.WorkplaceScreenModel
import city.zouitel.systemDesign.Cons.KEY_CLICK
import city.zouitel.systemDesign.DataStoreScreenModel
import city.zouitel.systemDesign.Icons.REDO_ICON
import city.zouitel.systemDesign.Icons.UNDO_ICON
import city.zouitel.systemDesign.SoundEffect

// TODO: need improvement.
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UndoRedo(
    dataStoreModel: DataStoreScreenModel,
    workplaceModel: WorkplaceScreenModel,
    titleState: TextFieldState?,
    descriptionState: TextFieldState?,
) {
    val context = LocalContext.current
    val thereIsSoundEffect = remember(dataStoreModel, dataStoreModel::getSound).collectAsState()
    val sound = SoundEffect()

    val uiState = workplaceModel.uiState

    IconButton(
        modifier = Modifier.size(23.dp),
        onClick = {
            sound.makeSound(context, KEY_CLICK, thereIsSoundEffect.value)
            if (uiState.isTitleFieldFocused) titleState?.undoState?.undo()
            else if (uiState.isDescriptionFieldFocused) descriptionState?.undoState?.undo()
            else throw Exception("there is no field is focused!!")

//            if (titleState.second) titleState.first?.undoState?.undo() else descriptionState?.undoState?.undo()
        },
        enabled =
            titleState?.undoState?.canUndo!! or descriptionState?.undoState?.canUndo!!
//        if (titleState.second) titleState.first?.undoState?.canUndo!! else descriptionState?.undoState?.canUndo!!
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
//            if (titleState.second) titleState.first?.undoState?.redo() else descriptionState?.undoState?.redo()

            if (uiState.isTitleFieldFocused) titleState.undoState.redo()
            else if (uiState.isDescriptionFieldFocused) descriptionState.undoState.redo()
            else throw Exception("there is no field is focused!!")
        },
        enabled =
        titleState.undoState.canRedo or descriptionState.undoState.canRedo

//        if (titleState.second) titleState.first?.undoState?.canRedo!! else descriptionState?.undoState?.canRedo!!,
    ) {
        Icon(
            painter = painterResource(id = REDO_ICON),
            contentDescription = null,
            tint = contentColorFor(backgroundColor = MaterialTheme.colorScheme.surfaceVariant),
        )
    }
}
