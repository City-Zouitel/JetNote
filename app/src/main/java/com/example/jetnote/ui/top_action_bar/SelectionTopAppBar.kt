package com.example.jetnote.ui.top_action_bar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetnote.db.entities.note.Note
import com.example.jetnote.db.entities.note_and_label.NoteAndLabel
import com.example.jetnote.db.entities.note_and_todo.NoteAndTodo
import com.example.jetnote.db.entities.todo.Todo
import com.example.jetnote.fp.copyNote
import com.example.jetnote.fp.sharNote
import com.example.jetnote.icons.COPY_ICON
import com.example.jetnote.icons.CROSS_ICON
import com.example.jetnote.icons.SHARE_ICON
import com.example.jetnote.icons.TRASH_ICON
import com.example.jetnote.ui.AdaptingRow
import com.example.jetnote.vm.*
import java.util.*
import kotlin.random.Random.Default.nextLong

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectionTopAppBar(
    noteVM: NoteVM = hiltViewModel(),
    noteAndLabelVM: NoteAndLabelVM = hiltViewModel(),
    labelVM: LabelVM = hiltViewModel(),
    todoVM: TodoVM = hiltViewModel(),
    noteAndTodoVM: NoteAndTodoVM = hiltViewModel(),
    selectionState: MutableState<Boolean>?,
    selectedNotes: SnapshotStateList<Note>?
) {
    val ctx = LocalContext.current
    val observeNotesAndLabels =
        remember(noteAndLabelVM, noteAndLabelVM::getAllNotesAndLabels).collectAsState()
    val observeLabels = remember(labelVM, labelVM::getAllLabels).collectAsState()

    val observeTodoList = remember(todoVM, todoVM::getAllTodoList).collectAsState()
    val observeNoteAndTodo =
        remember(noteAndTodoVM, noteAndTodoVM::getAllNotesAndTodo).collectAsState()

    val newUid = UUID.randomUUID()

    TopAppBar(
        navigationIcon = {

        },
        title = {
            Row {
                // delete
                Icon(painter = painterResource(id = TRASH_ICON), contentDescription = null,
                    modifier = Modifier
                        .padding(7.dp)
                        .clickable {
                        selectedNotes?.forEach {
                            noteVM.updateNote(
                                Note(
                                    title = it.title,
                                    description = it.description,
                                    priority = it.priority,
                                    uid = it.uid,
                                    color = it.color,
                                    textColor = it.textColor,
                                    trashed = 1
                                )
                            )
                        }
                        selectedNotes?.clear()
                        selectionState?.value = false
                    })

                // share
                AnimatedVisibility(visible = selectedNotes?.count() == 1) {
                    Row {
                        Icon(painter = painterResource(id = SHARE_ICON), contentDescription = null,
                            modifier = Modifier
                                .padding(7.dp)
                                .clickable {
                                sharNote(
                                    ctx,
                                    selectedNotes?.single()?.title!!,
                                    selectedNotes.single().description!!
                                ) {
                                    selectedNotes.clear()
                                    selectionState?.value = false
                                }
                            })
                        // copy

                        Icon(painter = painterResource(id = COPY_ICON), contentDescription = null,
                            modifier = Modifier
                                .padding(7.dp)
                                .clickable {
                                copyNote(ctx, noteVM, selectedNotes?.single()!!, newUid) {
                                    // copy each label.
                                    observeLabels.value.filter {
                                        observeNotesAndLabels.value.contains(
                                            NoteAndLabel(selectedNotes.single().uid, it.id)
                                        )
                                    }.forEach {
                                        noteAndLabelVM.addNoteAndLabel(
                                            NoteAndLabel(
                                                noteUid = newUid.toString(),
                                                labelId = it.id
                                            )
                                        )
                                    }

                                    // copy each todo item.
                                    observeTodoList.value.filter {
                                        observeNoteAndTodo.value.contains(
                                            NoteAndTodo(selectedNotes.single().uid, it.id)
                                        )
                                    }.forEach { todo ->
                                        nextLong().let {
                                            todoVM.addTotoItem(Todo(it, todo.item, todo.isDone))
                                            noteAndTodoVM.addNoteAndTodoItem(
                                                NoteAndTodo(
                                                    newUid.toString(),
                                                    it
                                                )
                                            )
                                        }
                                    }
                                }
                                selectedNotes.clear()
                                selectionState?.value = false
                            }
                        )
                    }
                }
            }
        },
        actions = {
            AdaptingRow(
                modifier = Modifier.width(60.dp)
            ) {
                Icon(
                    painter = painterResource(id = CROSS_ICON),
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        selectionState?.value = false
                        selectedNotes?.clear()
                    }
                )
                // number of selected notes.
                Text(text = selectedNotes?.count().toString(), fontSize = 24.sp)
            }
        }
    )
}