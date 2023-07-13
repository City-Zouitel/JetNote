package com.example.graph.home_screen

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.State
import com.example.common_ui.Cons.TRASH_MESSAGE
import com.example.common_ui.Cons.UNDO
import com.example.note.DataViewModel
import com.example.note.model.Data
import com.example.note.model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal fun UndoSnackbar(
    viewModule: DataViewModel,
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