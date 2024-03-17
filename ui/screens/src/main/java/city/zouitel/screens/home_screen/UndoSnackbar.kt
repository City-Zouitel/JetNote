package city.zouitel.screens.home_screen

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.State
import city.zouitel.note.DataScreenModel
import city.zouitel.note.model.Data
import city.zouitel.note.model.Note
import city.zouitel.systemDesign.Cons.TRASH_MESSAGE
import city.zouitel.systemDesign.Cons.UNDO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal fun UndoSnackbar(
    viewModule: DataScreenModel,
    scaffoldState : ScaffoldState,
    scope : CoroutineScope,
    trashedNotesState : State<List<Note>>
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
                    viewModule.editData(
                        Data(
                            title = trashedNotesState.value.last().dataEntity.title,
                            description = trashedNotesState.value.last().dataEntity.description,
                            priority = trashedNotesState.value.last().dataEntity.priority,
                            uid = trashedNotesState.value.last().dataEntity.uid,
                            textColor = trashedNotesState.value.last().dataEntity.textColor,
                            date = trashedNotesState.value.last().dataEntity.date,
                            color = trashedNotesState.value.last().dataEntity.color,
                            trashed = 0,
                        )
                    )
                }
            }
        }
    }
    return onShowSnackbar
}