package city.zouitel.note.ui.bottom_bar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.TextFieldState
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
import city.zouitel.systemDesign.CommonConstants.KEY_CLICK
import city.zouitel.systemDesign.DataStoreScreenModel
import city.zouitel.systemDesign.CommonIcons.REDO_ICON
import city.zouitel.systemDesign.CommonIcons.UNDO_ICON
import city.zouitel.systemDesign.SoundEffect

// TODO: need improvement.
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UndoRedo(
    dataStoreModel: DataStoreScreenModel,
    workspaceModel: WorkplaceScreenModel,
    titleState: TextFieldState?,
    descriptionState: TextFieldState?,
) {
    val context = LocalContext.current
    val thereIsSoundEffect = remember(dataStoreModel, dataStoreModel::getSound).collectAsState()
    val uiState by remember(workspaceModel, workspaceModel::uiState).collectAsState()
    val sound = SoundEffect()

    IconButton(
        modifier = Modifier.size(23.dp),
        onClick = {
            sound.makeSound(context, KEY_CLICK, thereIsSoundEffect.value)
            if (uiState.isTitleFieldFocused) titleState?.undoState?.undo()
            else if (uiState.isDescriptionFieldFocused) descriptionState?.undoState?.undo()
            else throw Exception("there is no field is focused!!")
                  },
        enabled =
            titleState?.undoState?.canUndo!! or descriptionState?.undoState?.canUndo!!
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

            if (uiState.isTitleFieldFocused) titleState.undoState.redo()
            else if (uiState.isDescriptionFieldFocused) descriptionState.undoState.redo()
            else throw Exception("there is no field is focused!!")
        },
        enabled =
        titleState.undoState.canRedo or descriptionState.undoState.canRedo

    ) {
        Icon(
            painter = painterResource(id = REDO_ICON),
            contentDescription = null,
            tint = contentColorFor(backgroundColor = MaterialTheme.colorScheme.surfaceVariant),
        )
    }
}
