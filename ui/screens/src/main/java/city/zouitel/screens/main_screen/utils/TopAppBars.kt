package city.zouitel.screens.main_screen.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import city.zouitel.domain.utils.Action
import city.zouitel.logic.events.UiEvent
import city.zouitel.logic.sharNote
import city.zouitel.note.model.Data
import city.zouitel.note.ui.DataScreenModel
import city.zouitel.screens.main_screen.MainScreenModel
import city.zouitel.screens.utils.sound
import city.zouitel.systemDesign.CommonConstants
import city.zouitel.systemDesign.CommonIcons
import city.zouitel.systemDesign.CommonPopupTip
import city.zouitel.systemDesign.CommonRow
import city.zouitel.systemDesign.DataStoreScreenModel
import city.zouitel.systemDesign.Open_Drawer
import city.zouitel.tags.model.NoteAndTag
import city.zouitel.tags.ui.NoteAndTagScreenModel
import city.zouitel.tags.ui.TagScreenModel
import city.zouitel.tasks.model.Task
import city.zouitel.tasks.ui.TaskScreenModel
import kotlin.random.Random
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

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
                AnimatedVisibility(uiState.isHomeScreen.not()) {
                    BroomData()
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalUuidApi::class
)
@Composable
internal fun HomeSelectionTopAppBar(
    datastoreModel: DataStoreScreenModel,
    mainModel: MainScreenModel,
    dataModel: DataScreenModel,
    noteAndTagModel: NoteAndTagScreenModel,
    tagModel: TagScreenModel,
    taskModel: TaskScreenModel,
    undo: (Data) -> Unit
) {

    val context = LocalContext.current
    val uiState by remember(mainModel, mainModel::uiState).collectAsState()
    val thereIsSoundEffect = remember(datastoreModel, datastoreModel::isMute).collectAsState()
    val newUid by lazy { Uuid.random().toString() }
    val observeNotesAndLabels =
        remember(noteAndTagModel, noteAndTagModel::observeAll).collectAsState()
    val observeLabels = remember(tagModel, tagModel::observeAll).collectAsState()

    val observeTodoList =
        remember(taskModel, taskModel::getAllTaskList).collectAsState()

    TopAppBar(
        navigationIcon = {
            Row {
                // remove a note.
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
                                sound.performSoundEffect(context, CommonConstants.KEY_CLICK, thereIsSoundEffect.value)
                                uiState.selectedNotes.forEach {
                                    dataModel.sendUiEvent(
                                        UiEvent.Update(
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
                                    )

                                    // to.do cancel the alarm manager reminder.
                                    undo.invoke(it)
                                }
                                mainModel.clearSelectionNotes()
                                mainModel.updateSelection(false)
                            }
                    )
                }

                AnimatedVisibility(visible = uiState.selectedNotes.count() == 1) {
                    Row {
                        // share a note
                        CommonPopupTip(message = "Share Note") {
                            Icon(painter = painterResource(id = CommonIcons.SHARE_ICON),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(7.dp)
                                    .combinedClickable(
                                        onLongClick = {
                                            it.showAlignBottom()
                                        }
                                    ) {
                                        sound.performSoundEffect(context, CommonConstants.KEY_CLICK, thereIsSoundEffect.value)

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

                        // copy a note.
                        CommonPopupTip(message = "Copy Note") {
                            Icon(painter = painterResource(id = CommonIcons.COPY_ICON),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(7.dp)
                                    .combinedClickable(
                                        onLongClick = {
                                            it.showAlignBottom()
                                        }
                                    ) {
                                        sound.performSoundEffect(context, CommonConstants.KEY_CLICK, thereIsSoundEffect.value)
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
                                                    noteAndTagModel.sendAction(
                                                        Action.Insert(NoteAndTag(newUid, it.id))
                                                    )
                                                }

                                            // copy each todo item.
                                            observeTodoList.value
                                                .forEach { todo ->
                                                    Random.nextLong()
                                                        .let {
                                                            taskModel.sendAction(
                                                                Action.Insert(Task(it, newUid, todo.item, todo.isDone))
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
    val thereIsSoundEffect = remember(datastoreModel, datastoreModel::isMute).collectAsState()
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
                                    onLongClick = { it.showAlignBottom() }
                                ) {
                                    sound.performSoundEffect(context, CommonConstants.KEY_CLICK, thereIsSoundEffect.value)
                                    uiState.selectedNotes.forEach { dataModel.sendUiEvent(UiEvent.Delete(it)) }
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
                                    sound.performSoundEffect(context, CommonConstants.KEY_CLICK, thereIsSoundEffect.value)
                                    uiState.selectedNotes.forEach {
                                        dataModel.sendUiEvent(UiEvent.Update(it.copy(removed = 0)))
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