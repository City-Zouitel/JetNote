package city.zouitel.screens.top_action_bar.selection_bars

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
import city.zouitel.note.DataScreenModel
import city.zouitel.note.model.Data
import city.zouitel.systemDesign.CommonRow
import city.zouitel.systemDesign.Cons.KEY_CLICK
import city.zouitel.systemDesign.DataStoreVM
import city.zouitel.systemDesign.Icons.COPY_ICON
import city.zouitel.systemDesign.Icons.SHARE_ICON
import city.zouitel.systemDesign.Icons.REMOVE_ICON
import city.zouitel.systemDesign.CommonPopupTip
import city.zouitel.systemDesign.sharNote
import city.zouitel.tags.model.NoteAndTag
import city.zouitel.tags.viewmodel.TagScreenModel
import city.zouitel.tasks.viewmodel.NoteAndTaskScreenModel
import city.zouitel.tasks.model.NoteAndTask
import city.zouitel.tasks.model.Task
import city.zouitel.screens.sound
import city.zouitel.notifications.viewmodel.NotificationVM
import city.zouitel.tags.viewmodel.NoteAndTagScreenModel
import city.zouitel.tasks.viewmodel.TaskScreenModel
import org.koin.androidx.compose.koinViewModel
import java.util.*
import kotlin.random.Random.Default.nextLong

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeSelectionTopAppBar(
    dataStoreVM: DataStoreVM = koinViewModel(),
    notificationVM: NotificationVM = koinViewModel(),
    dataModel: DataScreenModel,
    tagModel: TagScreenModel,
    noteAndTagModel: NoteAndTagScreenModel,
    taskModel: TaskScreenModel,
    noteAndTaskModel: NoteAndTaskScreenModel,
    homeSelectionState: MutableState<Boolean>?,
    selectedNotes: SnapshotStateList<Data>?,
    undo: (Data) -> Unit
) {
    val context = LocalContext.current
    val thereIsSoundEffect = remember(dataStoreVM, dataStoreVM::getSound).collectAsState()

    val observeNotesAndLabels =
        remember(noteAndTagModel, noteAndTagModel::getAllNotesAndTags).collectAsState()
    val observeLabels = remember(tagModel, tagModel::getAllLTags).collectAsState()

    val observeTodoList =
        remember(taskModel, taskModel::getAllTaskList).collectAsState()
    val observeNoteAndTodo =
        remember(noteAndTaskModel, noteAndTaskModel::getAllNotesAndTask).collectAsState()

    val newUid = UUID.randomUUID()

    TopAppBar(
        navigationIcon = {
            Row {
                // remove.
                CommonPopupTip(message = "Remove") {
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
                                sound.makeSound.invoke(
                                    context,
                                    KEY_CLICK,
                                    thereIsSoundEffect.value
                                )
                                selectedNotes?.forEach {
                                    dataModel.editData(
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
                        CommonPopupTip(message = "Share WidgetNote") {
                            Icon(painter = painterResource(id = SHARE_ICON),
                                contentDescription = null,
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
                        CommonPopupTip(message = "Copy WidgetNote") {
                            Icon(painter = painterResource(id = COPY_ICON),
                                contentDescription = null,
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
                                            dataModel,
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
                                                    noteAndTagModel.addNoteAndTag(
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
                                                        taskModel.addTotoItem(
                                                            Task(
                                                                it,
                                                                todo.item,
                                                                todo.isDone
                                                            )
                                                        )
                                                        noteAndTaskModel.addNoteAndTaskItem(
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
            CommonRow(
                Modifier.padding(start = 10.dp, end = 10.dp),
            ) {
                SelectionCount(
                    selectionState = homeSelectionState,
                    selectedNotes = selectedNotes
                )
            }
        }
    )
}