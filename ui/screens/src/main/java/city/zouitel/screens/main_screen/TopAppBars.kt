package city.zouitel.screens.main_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import city.zouitel.systemDesign.sharNote
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
    homeModel: MainScreenModel,
    scrollBehavior: TopAppBarScrollBehavior,
    drawerState: DrawerState,
) {
    val uiState by lazy { homeModel.uiState }

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
                homeModel = homeModel
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
                            mainModel = homeModel
                        )
                    } else {
                        BroomData(homeModel = homeModel)
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
    homeModel: MainScreenModel,
    dataModel: DataScreenModel,
    notificationModel: NotificationScreenModel,
    noteAndTagModel: NoteAndTagScreenModel,
    tagModel: TagScreenModel,
    taskModel: TaskScreenModel,
    noteAndTaskModel: NoteAndTaskScreenModel,
    undo: (Data) -> Unit
) {

    val context = LocalContext.current
    val uiState by lazy { homeModel.uiState }
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
                                            trashed = 1
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
                                homeModel.clearSelectionNotes()
                                homeModel.updateSelection(false)
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
                                            homeModel.clearSelectionNotes()
                                            homeModel.updateSelection(false)
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
                                        homeModel.clearSelectionNotes()
                                        homeModel.updateSelection(false)
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
                    homeModel = homeModel
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun RemovedSelectionTopAppBar(
    datastoreModel: DataStoreScreenModel,
    homeModel: MainScreenModel,
    dataModel: DataScreenModel
) {
    val context = LocalContext.current
    val thereIsSoundEffect = remember(datastoreModel, datastoreModel::getSound).collectAsState()
    val uiState by lazy { homeModel.uiState }

    TopAppBar(
        navigationIcon = {
            // wipe notes.
            CommonPopupTip(message = "Wipe Notes") {
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
                                dataModel.deleteData(it)
                            }
                            homeModel.clearSelectionNotes()
                            homeModel.updateSelection(false)
                        }
                )
            }

        },
        title = {},
        actions = {
            CommonRow(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
            ) {
                SelectionsCount(homeModel = homeModel)
            }
        }
    )
}


