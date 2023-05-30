package com.example.graph.home_screen

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.State
import com.example.common_ui.Cons.TRASH_MESSAGE
import com.example.common_ui.Cons.UNDO
import com.example.local.model.relational.NoteEntity
import com.example.local.model.Note
import com.example.note.NoteVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal fun UndoSnackbar(
    viewModule: NoteVM,
    scaffoldState : ScaffoldState,
    scope : CoroutineScope,
    trashedNotesState : State<List<NoteEntity>>
): (Note) -> Unit {
    val onShowSnackbar: (Note) -> Unit = { note ->

        scope.launch {
            val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                message = note.title + " " + TRASH_MESSAGE,
                actionLabel = UNDO
            )
            when(snackbarResult)  {
                SnackbarResult.Dismissed -> {} // Timber.d("Snackbar dismissed")
                SnackbarResult.ActionPerformed -> {
                    viewModule.updateNote(
                        Note(
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