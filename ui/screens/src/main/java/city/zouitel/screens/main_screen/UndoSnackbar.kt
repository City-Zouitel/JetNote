package city.zouitel.screens.main_screen

import androidx.compose.material.*
import androidx.compose.material.SnackbarResult
import androidx.compose.material3.*
import androidx.compose.runtime.State
import city.zouitel.note.ui.DataScreenModel
import city.zouitel.note.model.Data
import city.zouitel.note.model.Note
import city.zouitel.systemDesign.CommonConstants.TRASH_MESSAGE
import city.zouitel.systemDesign.CommonConstants.UNDO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal fun UndoSnackbar(
    dataModule: DataScreenModel,
    scaffoldState : ScaffoldState,
    scope : CoroutineScope,
    removedNotesState : State<List<Note>>
): (Data) -> Unit {
    val onShowSnackbar: (Data) -> Unit = { note ->

        scope.launch {
            val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                message = note.title + " " + TRASH_MESSAGE,
                actionLabel = UNDO
            )

            when(snackbarResult)  {
                SnackbarResult.Dismissed -> {} // Timber.d("Snackbar dismissed")
                SnackbarResult.ActionPerformed -> {
                    dataModule.editData(
                        Data(
                            title = removedNotesState.value.last().dataEntity.title,
                            description = removedNotesState.value.last().dataEntity.description,
                            priority = removedNotesState.value.last().dataEntity.priority,
                            uid = removedNotesState.value.last().dataEntity.uid,
                            textColor = removedNotesState.value.last().dataEntity.textColor,
                            date = removedNotesState.value.last().dataEntity.date,
                            color = removedNotesState.value.last().dataEntity.color,
                            removed = 0,
                        )
                    )
                }
            }
        }
    }
    return onShowSnackbar
}