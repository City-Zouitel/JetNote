package com.example.grqph.home_screen

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.State
import com.example.common_ui.Cons.TRASH_MESSAGE
import com.example.common_ui.Cons.UNDO
import com.example.local.model.Entity
import com.example.local.model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal fun UndoSnackbar(
    viewModule: com.example.note.NoteVM,
    scaffoldState : ScaffoldState,
    scope : CoroutineScope,
    trashedNotesState : State<List<Entity>>
): (Note) -> Unit {
    val onShowSnackbar: (Note) -> Unit = { note ->

        scope.launch {
            val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                message = note.title +  " " + TRASH_MESSAGE,
                actionLabel = UNDO
            )
            when(snackbarResult)  {
                SnackbarResult.Dismissed -> {} // Timber.d("Snackbar dismissed")
                SnackbarResult.ActionPerformed -> {
                    viewModule.updateNote(
                        Note(
                            title = trashedNotesState.value.last().note.title,
                            description = trashedNotesState.value.last().note.description,
                            priority = trashedNotesState.value.last().note.priority,
                            uid = trashedNotesState.value.last().note.uid,
                            textColor = trashedNotesState.value.last().note.textColor,
                            date = trashedNotesState.value.last().note.date,
                            color = trashedNotesState.value.last().note.color,
                            trashed = 0,
                        )
                    )
                }
            }
        }
    }
    return onShowSnackbar
}