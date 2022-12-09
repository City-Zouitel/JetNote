package com.example.jetnote.ui.snackebars

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.State
import com.example.jetnote.cons.TRASH_MESSAGE
import com.example.jetnote.cons.UNDO
import com.example.jetnote.db.entities.Entity
import com.example.jetnote.db.entities.note.Note
import com.example.jetnote.vm.NoteVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

internal fun UndoSnackbar(
    viewModule: NoteVM,
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
                SnackbarResult.Dismissed -> Timber.d("Snackbar dismissed")
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