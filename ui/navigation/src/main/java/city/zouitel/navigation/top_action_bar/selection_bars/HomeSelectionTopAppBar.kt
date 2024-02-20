package city.zouitel.navigation.top_action_bar.selection_bars

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
import city.zouitel.note.DataViewModel
import city.zouitel.note.model.Data
import city.zouitel.systemDesign.AdaptingRow
import city.zouitel.systemDesign.Cons.KEY_CLICK
import city.zouitel.systemDesign.DataStoreVM
import city.zouitel.systemDesign.Icons.COPY_ICON
import city.zouitel.systemDesign.Icons.SHARE_ICON
import city.zouitel.systemDesign.Icons.REMOVE_ICON
import city.zouitel.systemDesign.PopupTip
import city.zouitel.systemDesign.sharNote
import city.zouitel.tags.model.NoteAndTag
import city.zouitel.tags.viewmodel.NoteAndTagViewModel
import city.zouitel.tags.viewmodel.TagViewModel
import city.zouitel.tasks.viewmodel.NoteAndTaskViewModel
import city.zouitel.tasks.viewmodel.TaskViewModel
import city.zouitel.tasks.model.NoteAndTask
import city.zouitel.tasks.model.Task
import city.zouitel.navigation.sound
import city.zouitel.notifications.viewmodel.NotificationVM
import org.koin.androidx.compose.koinViewModel
import java.util.*
import kotlin.random.Random.Default.nextLong

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeSelectionTopAppBar(
    dataViewModel: DataViewModel = koinViewModel(),
    noteAndTagViewModel: NoteAndTagViewModel = koinViewModel(),
    tagViewModel: TagViewModel = koinViewModel(),
    taskViewModel: TaskViewModel = koinViewModel(),
    noteAndTodoVM: NoteAndTaskViewModel = koinViewModel(),
    dataStoreVM: DataStoreVM = koinViewModel(),
    notificationVM: NotificationVM = koinViewModel(),
    homeSelectionState: MutableState<Boolean>?,
    selectedNotes: SnapshotStateList<Data>?,
    undo: (Data) -> Unit
) {
    val context = LocalContext.current
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
            // remove.
            PopupTip(message = "Remove") {
                Icon(
                    painter = painterResource(id = REMOVE_ICON),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(7.dp)
                        .combinedClickable(
                            onLongClick = {
                                it.showAlignBottom()
                            }
                        ) {
                            sound.makeSound.invoke(context, KEY_CLICK, thereIsSoundEffect.value)
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

                                // to cancel the alarm manager reminding.
                                notificationVM.scheduleNotification(
                                    context = context,
                                    dateTime = it.reminding,
                                    title = it.title,
                                    message = it.description,
                                    uid = it.uid,
                                    onReset = { true }
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
                    PopupTip(message = "Share WidgetNote") {
                        Icon(painter = painterResource(id = SHARE_ICON), contentDescription = null,
                            modifier = Modifier
                                .padding(7.dp)
                                .combinedClickable(
                                    onLongClick = {
                                        it.showAlignBottom()
                                    }
                                ) {
                                    sound.makeSound.invoke(
                                        context,
                                        KEY_CLICK,
                                        thereIsSoundEffect.value
                                    )

                                    sharNote(
                                        context,
                                        selectedNotes?.single()?.title!!,
                                        selectedNotes.single().description!!
                                    ) {
                                        selectedNotes.clear()
                                        homeSelectionState?.value = false
                                    }
                                })
                    }

                    // copy the dataEntity.
                    PopupTip(message = "Copy WidgetNote") {
                        Icon(painter = painterResource(id = COPY_ICON), contentDescription = null,
                            modifier = Modifier
                                .padding(7.dp)
                                .combinedClickable(
                                    onLongClick = {
                                        it.showAlignBottom()
                                    }
                                ) {
                                    sound.makeSound.invoke(
                                        context,
                                        KEY_CLICK,
                                        thereIsSoundEffect.value
                                    )

                                    copyNote(
                                        context,
                                        dataViewModel,
                                        selectedNotes?.single()!!,
                                        newUid
                                    ) {
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