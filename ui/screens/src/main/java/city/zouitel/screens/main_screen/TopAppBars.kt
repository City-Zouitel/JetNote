package city.zouitel.screens.main_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import city.zouitel.logic.sharNote
import city.zouitel.note.model.Data
import city.zouitel.note.ui.DataScreenModel
import city.zouitel.notifications.viewmodel.NotificationScreenModel
import city.zouitel.screens.copyNote
import city.zouitel.screens.sound
import city.zouitel.systemDesign.CommonPopupTip
import city.zouitel.systemDesign.CommonRow
import city.zouitel.systemDesign.CommonConstants
import city.zouitel.systemDesign.DataStoreScreenModel
import city.zouitel.systemDesign.CommonIcons
import city.zouitel.systemDesign.Open_Drawer
import city.zouitel.tags.model.NoteAndTag
import city.zouitel.tags.ui.NoteAndTagScreenModel
import city.zouitel.tags.ui.TagScreenModel
import city.zouitel.tasks.model.NoteAndTask
import city.zouitel.tasks.model.Task
import city.zouitel.tasks.ui.NoteAndTaskScreenModel
import city.zouitel.tasks.ui.TaskScreenModel
import java.util.UUID
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainTopAppBar(
    datastoreModel: DataStoreScreenModel,
    mainModel: MainScreenModel,
    scrollBehavior: TopAppBarScrollBehavior,
    drawerState: DrawerState,
) {
    val uiState by remember(mainModel, mainModel::uiState).collectAsState()

    TopAppBar(
        navigationIcon = {
            Row {
                AnimatedVisibility(uiState.searchTitle.isEmpty() && uiState.searchTag == null) {
                    CommonRow(
                        Modifier.padding(start = 10.dp, end = 10.dp),
                    ) {
                        Open_Drawer(datastoreModel, drawerState)
                    }
                }
            }
        },
        title = {
            SearchField(
                dataStoreModel = datastoreModel,
                mainModel = mainModel
            )
        },
        actions = {
            AnimatedVisibility(uiState.searchTitle.isEmpty() && uiState.searchTag == null) {
                CommonRow(
                    Modifier.padding(start = 10.dp, end = 10.dp),
                ) {
                    if (uiState.isHomeScreen) {
                        SortBy(
                            dataStoreModel = datastoreModel,
                            mainModel = mainModel
                        )
                    } else {
                        BroomData(homeModel = mainModel)
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    Layout(datastoreModel)
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun HomeSelectionTopAppBar(
    datastoreModel: DataStoreScreenModel,
    mainModel: MainScreenModel,
    dataModel: DataScreenModel,
    notificationModel: NotificationScreenModel,
    noteAndTagModel: NoteAndTagScreenModel,
    tagModel: TagScreenModel,
    taskModel: TaskScreenModel,
    noteAndTaskModel: NoteAndTaskScreenModel,
    undo: (Data) -> Unit
) {

    val context = LocalContext.current
    val uiState by remember(mainModel, mainModel::uiState).collectAsState()
    val thereIsSoundEffect = remember(datastoreModel, datastoreModel::getSound).collectAsState()
    val newUid by lazy { UUID.randomUUID() }
    val observeNotesAndLabels =
        remember(noteAndTagModel, noteAndTagModel::getAllNotesAndTags).collectAsState()
    val observeLabels = remember(tagModel, tagModel::getAllLTags).collectAsState()

    val observeTodoList =
        remember(taskModel, taskModel::getAllTaskList).collectAsState()
    val observeNoteAndTodo =
        remember(noteAndTaskModel, noteAndTaskModel::getAllNotesAndTask).collectAsState()
    TopAppBar(
        navigationIcon = {
            Row {
                // remove.
                CommonPopupTip(message = "Remove") {
                    Icon(
                        painter = painterResource(id = CommonIcons.REMOVE_ICON),
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
                                    CommonConstants.KEY_CLICK,
                                    thereIsSoundEffect.value
                                )
                                uiState.selectedNotes.forEach {
                                    dataModel.editData(
                                        Data(
                                            title = it.title,
                                            description = it.description,
                                            priority = it.priority,
                                            uid = it.uid,
                                            color = it.color,
                                            textColor = it.textColor,
                                            removed = 1
                                        )
                                    )

                                    // to cancel the alarm manager reminding.
                                    notificationModel.scheduleNotification(
                                        context = context,
                                        dateTime = it.reminding,
                                        title = it.title,
                                        message = it.description,
                                        uid = it.uid,
                                        onReset = { true }
                                    )
                                    undo.invoke(it)
                                }
                                mainModel.clearSelectionNotes()
                                mainModel.updateSelection(false)
                            }
                    )
                }

                AnimatedVisibility(visible = uiState.selectedNotes.count() == 1) {
                    Row {
                        // share
                        CommonPopupTip(message = "Share WidgetNote") {
                            Icon(painter = painterResource(id = CommonIcons.SHARE_ICON),
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
                                            CommonConstants.KEY_CLICK,
                                            thereIsSoundEffect.value
                                        )

                                        sharNote(
                                            context,
                                            uiState.selectedNotes.single().title!!,
                                            uiState.selectedNotes.single().description!!
                                        ) {
                                            mainModel.clearSelectionNotes()
                                            mainModel.updateSelection(false)
                                        }
                                    })
                        }

                        // copy the dataEntity.
                        CommonPopupTip(message = "Copy WidgetNote") {
                            Icon(painter = painterResource(id = CommonIcons.COPY_ICON),
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
                                            CommonConstants.KEY_CLICK,
                                            thereIsSoundEffect.value
                                        )

                                        copyNote(
                                            context,
                                            dataModel,
                                            uiState.selectedNotes.single(),
                                            newUid
                                        ) {
                                            // copy each label.
                                            observeLabels.value
                                                .filter {
                                                    observeNotesAndLabels.value.contains(
                                                        NoteAndTag(
                                                            uiState.selectedNotes.single().uid,
                                                            it.id
                                                        )
                                                    )
                                                }
                                                .forEach {
                                                    noteAndTagModel.addNoteAndTag(
                                                        NoteAndTag(
                                                            newUid.toString(),
                                                            it.id
                                                        )
                                                    )
                                                }

                                            // copy each todo item.
                                            observeTodoList.value
                                                .filter {
                                                    observeNoteAndTodo.value.contains(
                                                        NoteAndTask(
                                                            uiState.selectedNotes.single().uid,
                                                            it.id
                                                        )
                                                    )
                                                }
                                                .forEach { todo ->
                                                    Random
                                                        .nextLong()
                                                        .let {
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
                                        mainModel.clearSelectionNotes()
                                        mainModel.updateSelection(false)
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
                SelectionsCount(
                    mainModel = mainModel
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun RemovedSelectionTopAppBar(
    datastoreModel: DataStoreScreenModel,
    mainModel: MainScreenModel,
    dataModel: DataScreenModel
) {
    val context = LocalContext.current
    val thereIsSoundEffect = remember(datastoreModel, datastoreModel::getSound).collectAsState()
    val uiState by remember(mainModel, mainModel::uiState).collectAsState()

    TopAppBar(
        navigationIcon = {
            Row {
                CommonRow {
                    // wipe notes.
                    CommonPopupTip(message = "Erase Notes") {
                        Icon(
                            painter = painterResource(id = CommonIcons.ERASER_ICON),
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
                                        CommonConstants.KEY_CLICK,
                                        thereIsSoundEffect.value
                                    )

                                    uiState.selectedNotes.forEach {
                                        dataModel.deleteData(it)
                                    }
                                    mainModel.clearSelectionNotes()
                                    mainModel.updateSelection(false)
                                }
                        )
                    }
                    CommonPopupTip(message = "Rollback Notes") {
                        Icon(
                            painter = painterResource(id = CommonIcons.UNDO_ICON),
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.dp)
                                .combinedClickable(
                                    onLongClick = {
                                        it.showAlignBottom()
                                    }
                                ) {
                                    sound.makeSound.invoke(
                                        context,
                                        CommonConstants.KEY_CLICK,
                                        thereIsSoundEffect.value
                                    )

                                    uiState.selectedNotes.forEach {
                                        dataModel.editData(it.copy(removed = 0))
                                    }
                                    mainModel.clearSelectionNotes()
                                    mainModel.updateSelection(false)
                                }
                        )
                    }
                }
            }
        },
        title = {},
        actions = {
            CommonRow(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
            ) {
                SelectionsCount(mainModel = mainModel)
            }
        }
    )
}