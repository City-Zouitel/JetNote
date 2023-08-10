package com.example.graph.top_action_bar.selection_bars

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common_ui.AdaptingRow
import com.example.common_ui.Cons.KEY_CLICK
import com.example.common_ui.DataStoreVM
import com.example.common_ui.Icons.COPY_ICON
import com.example.common_ui.Icons.SHARE_ICON
import com.example.common_ui.Icons.TRASH_ICON
import com.example.common_ui.PopupTip
import com.example.common_ui.sharNote
import com.example.graph.copyNote
import com.example.graph.sound
import com.example.note.DataViewModel
import com.example.note.model.Data
import com.example.tags.viewmodel.TagViewModel
import com.example.tags.viewmodel.NoteAndTagViewModel
import com.example.tags.model.NoteAndTag
import com.example.tasks.NoteAndTaskViewModel
import com.example.tasks.TaskViewModel
import com.example.tasks.model.NoteAndTask
import com.example.tasks.model.Task
import java.util.*
import kotlin.random.Random.Default.nextLong

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeSelectionTopAppBar(
    dataViewModel: DataViewModel = hiltViewModel(),
    noteAndTagViewModel: NoteAndTagViewModel = hiltViewModel(),
    tagViewModel: TagViewModel = hiltViewModel(),
    taskViewModel: TaskViewModel = hiltViewModel(),
    noteAndTodoVM: NoteAndTaskViewModel = hiltViewModel(),
    dataStoreVM: DataStoreVM = hiltViewModel(),
    homeSelectionState: MutableState<Boolean>?,
    selectedNotes: SnapshotStateList<Data>?,
    undo: (Data) -> Unit
) {
    val ctx = LocalContext.current
    val thereIsSoundEffect = remember(dataStoreVM, dataStoreVM::getSound).collectAsState()

    val observeNotesAndLabels =
        remember(noteAndTagViewModel, noteAndTagViewModel::getAllNotesAndTags).collectAsState()
    val observeLabels = remember(tagViewModel, tagViewModel::getAllLTags).collectAsState()

    val observeTodoList = remember(taskViewModel, taskViewModel::getAllTaskList).collectAsState()
    val observeNoteAndTodo =
        remember(noteAndTodoVM, noteAndTodoVM::getAllNotesAndTask).collectAsState()

    val newUid = UUID.randomUUID()

    TopAppBar(
        navigationIcon = {
            Row {
            // delete
            PopupTip(message = "Move To Trash") {
                Icon(
                    painter = painterResource(id = TRASH_ICON),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(7.dp)
                        .combinedClickable(
                            onLongClick = {
                                it.showAlignBottom()
                            }
                        ) {
                            sound.makeSound.invoke(ctx, KEY_CLICK, thereIsSoundEffect.value)
                            selectedNotes?.forEach {
                                dataViewModel.editData(
                                    Data(
                                        title = it.title,
                                        description = it.description,
                                        priority = it.priority,
                                        uid = it.uid,
                                        color = it.color,
                                        textColor = it.textColor,
                                        trashed = 1
                                    )
                                )
                                undo.invoke(it)
                            }
                            selectedNotes?.clear()
                            homeSelectionState?.value = false
                        }
                )
            }

            AnimatedVisibility(visible = selectedNotes?.count() == 1) {
                Row {
                    // share
                    PopupTip(message = "Share Note") {
                        Icon(painter = painterResource(id = SHARE_ICON), contentDescription = null,
                            modifier = Modifier
                                .padding(7.dp)
                                .combinedClickable(
                                    onLongClick = {
                                        it.showAlignBottom()
                                    }
                                ) {
                                    sound.makeSound.invoke(
                                        ctx,
                                        KEY_CLICK,
                                        thereIsSoundEffect.value
                                    )

                                    sharNote(
                                        ctx,
                                        selectedNotes?.single()?.title!!,
                                        selectedNotes.single().description!!
                                    ) {
                                        selectedNotes.clear()
                                        homeSelectionState?.value = false
                                    }
                                })
                    }

                    // copy the dataEntity.
                    PopupTip(message = "Copy Note") {
                        Icon(painter = painterResource(id = COPY_ICON), contentDescription = null,
                            modifier = Modifier
                                .padding(7.dp)
                                .combinedClickable(
                                    onLongClick = {
                                        it.showAlignBottom()
                                    }
                                ) {
                                    sound.makeSound.invoke(
                                        ctx,
                                        KEY_CLICK,
                                        thereIsSoundEffect.value
                                    )

                                    copyNote(ctx, dataViewModel, selectedNotes?.single()!!, newUid) {
                                        // copy each label.
                                        observeLabels.value
                                            .filter {
                                                observeNotesAndLabels.value.contains(
                                                    NoteAndTag(
                                                        selectedNotes.single().uid,
                                                        it.id
                                                    )
                                                )
                                            }
                                            .forEach {
                                                noteAndTagViewModel.addNoteAndTag(
                                                    NoteAndTag(
                                                        noteUid = newUid.toString(),
                                                        labelId = it.id
                                                    )
                                                )
                                            }

                                        // copy each todo item.
                                        observeTodoList.value
                                            .filter {
                                                observeNoteAndTodo.value.contains(
                                                    NoteAndTask(
                                                        selectedNotes.single().uid,
                                                        it.id
                                                    )
                                                )
                                            }
                                            .forEach { todo ->
                                                nextLong().let {
                                                    taskViewModel.addTotoItem(
                                                        Task(
                                                            it,
                                                            todo.item,
                                                            todo.isDone
                                                        )
                                                    )
                                                    noteAndTodoVM.addNoteAndTaskItem(
                                                        NoteAndTask(
                                                            newUid.toString(),
                                                            it
                                                        )
                                                    )
                                                }
                                            }
                                    }
                                    selectedNotes.clear()
                                    homeSelectionState?.value = false
                                }
                        )
                    }
                }
            }
        }
        },
        title = {},
        actions = {
            AdaptingRow(
                Modifier.padding(start = 10.dp, end = 10.dp),
            ) {
                SelectionCount(selectionState = homeSelectionState, selectedNotes = selectedNotes)
            }
        }
    )
}