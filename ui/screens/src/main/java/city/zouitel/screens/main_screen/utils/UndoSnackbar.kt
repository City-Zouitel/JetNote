package city.zouitel.screens.main_screen.utils

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.State
import city.zouitel.domain.utils.Action
import city.zouitel.note.model.Data
import city.zouitel.note.model.Note
import city.zouitel.note.ui.DataScreenModel
import city.zouitel.systemDesign.CommonConstants.UNDO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal fun undoSnackbar(
    dataModule: DataScreenModel,
    scaffoldState : ScaffoldState,
    scope : CoroutineScope,
    archivedNotesState : State<List<Note>>
): (Data) -> Unit {
    val onShowSnackbar: (Data) -> Unit = { note ->

        scope.launch {
            val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                message = note.title + " " + "moved to archive.",
                actionLabel = UNDO
            )

            when (snackbarResult) {
                SnackbarResult.Dismissed -> {} // Timber.d("Snackbar dismissed")
                SnackbarResult.ActionPerformed -> {
                    dataModule.sendAction(
                        Action.Rollback(archivedNotesState.value.last().data.uid)
                    )
                }
            }
        }
    }
    return onShowSnackbar
}