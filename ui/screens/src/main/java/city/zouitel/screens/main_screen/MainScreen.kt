package city.zouitel.screens.main_screen

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import city.zouitel.audios.ui.component.AudioScreenModel
import city.zouitel.audios.ui.component.NoteAndAudioScreenModel
import city.zouitel.links.model.NoteAndLink
import city.zouitel.links.ui.LinkScreenModel
import city.zouitel.links.ui.NoteAndLinkScreenModel
import city.zouitel.media.ui.MediaScreenModel
import city.zouitel.media.ui.NoteAndMediaScreenModel
import city.zouitel.note.ui.DataScreenModel
import city.zouitel.note.ui.workplace.WorkplaceScreen
import city.zouitel.notifications.viewmodel.NotificationScreenModel
import city.zouitel.screens.navigation_drawer.NavigationDrawer
import city.zouitel.screens.note_card.NoteCard
import city.zouitel.screens.sound
import city.zouitel.systemDesign.CommonConstants
import city.zouitel.systemDesign.CommonConstants.BY_NAME
import city.zouitel.systemDesign.CommonConstants.ORDER_BY_NEWEST
import city.zouitel.systemDesign.CommonConstants.ORDER_BY_OLDEST
import city.zouitel.systemDesign.CommonConstants.ORDER_BY_PRIORITY
import city.zouitel.systemDesign.CommonConstants.ORDER_BY_REMINDER
import city.zouitel.systemDesign.CommonIcons
import city.zouitel.systemDesign.DataStoreScreenModel
import city.zouitel.tags.ui.NoteAndTagScreenModel
import city.zouitel.tags.ui.TagScreenModel
import city.zouitel.tasks.ui.NoteAndTaskScreenModel
import city.zouitel.tasks.ui.TaskScreenModel

data class MainScreen(val isHome: Boolean): Screen {

    @Composable
    override fun Content() {
        val mainModel = getScreenModel<MainScreenModel>()

        LaunchedEffect(isHome) {
            mainModel.updateScreen(isHome)
        }

        Main(
            mainModel = mainModel,
            notificationModel = getScreenModel(),
            tagModel = getScreenModel(),
            noteAndTagModel = getScreenModel(),
            taskModel = getScreenModel(),
            noteAndTaskModel = getScreenModel(),
            dataModel = getScreenModel(),
            audioModel = getScreenModel(),
            noteAndAudioModel = getScreenModel(),
            linkModel = getScreenModel(),
            noteAndLinkModel = getScreenModel(),
            datastoreModel = getScreenModel(),
            mediaModel = getScreenModel(),
            noteAndMediaModel = getScreenModel(),
        )
    }

    @SuppressLint(
        "UnusedMaterialScaffoldPaddingParameter",
        "UnusedMaterial3ScaffoldPaddingParameter"
    )
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Main(
        notificationModel: NotificationScreenModel,
        tagModel: TagScreenModel,
        noteAndTagModel: NoteAndTagScreenModel,
        taskModel: TaskScreenModel,
        noteAndTaskModel: NoteAndTaskScreenModel,
        mainModel: MainScreenModel,
        dataModel: DataScreenModel,
        audioModel: AudioScreenModel,
        noteAndAudioModel: NoteAndAudioScreenModel,
        linkModel: LinkScreenModel,
        noteAndLinkModel: NoteAndLinkScreenModel,
        datastoreModel: DataStoreScreenModel,
        mediaModel: MediaScreenModel,
        noteAndMediaModel: NoteAndMediaScreenModel
    ) {
        val context = LocalContext.current
        val navigator = LocalNavigator.currentOrThrow

        val uiState by remember(mainModel, mainModel::uiState).collectAsState()
        val currentLayout = remember(datastoreModel, datastoreModel::getLayout).collectAsState()
        val thereIsSoundEffect = remember(datastoreModel, datastoreModel::getSound).collectAsState()
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scaffoldState = rememberScaffoldState()

        // to observer notes while changing immediately.
        val observerNotes = if (uiState.isHomeScreen) {
            when (
                remember(datastoreModel, datastoreModel::getOrdination).collectAsState().value
            ) {
                BY_NAME -> mainModel.allNotesByName.collectAsState()
                ORDER_BY_OLDEST -> mainModel.allNotesByOldest.collectAsState()
                ORDER_BY_NEWEST -> mainModel.allNotesByNewest.collectAsState()
                ORDER_BY_PRIORITY -> mainModel.allNotesByPriority.collectAsState()
                ORDER_BY_REMINDER -> mainModel.allRemindingNotes.collectAsState()
                else -> mainModel.allNotesById.collectAsState()
            }
        } else {
            mainModel.allTrashedNotes.collectAsState()
        }

        val observerRemovedNotes = remember(mainModel, mainModel::allTrashedNotes).collectAsState()

        val filteredObserverLocalNotes by remember(observerNotes.value) {
            derivedStateOf {
                observerNotes.value.filter {
                    it.dataEntity.title?.contains(uiState.searchTitle, true)
                            ?: true || it.tagEntities.contains(uiState.searchTag)
                }
            }
        }

        val topAppBarState = rememberTopAppBarState()
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
        val listLayoutState = rememberLazyListState()
        val gridLayoutState = rememberLazyStaggeredGridState()
        val coroutineScope = rememberCoroutineScope()

        val getBackgroundColor = MaterialTheme.colorScheme.surface.toArgb()
        val getTextColor = MaterialTheme.colorScheme.onSurface.toArgb()

        //undo snack-bar.
        val undo = UndoSnackbar(
            dataModule = dataModel,
            scaffoldState = scaffoldState,
            scope = coroutineScope,
            removedNotesState = observerRemovedNotes
        )

        if (uiState.isErase) {
            EraseDialog(dataStoreModel = datastoreModel, homeModel = mainModel) {
                dataModel.eraseNotes()
                observerRemovedNotes.value.forEach { entity ->
                    entity.linkEntities.forEach { link ->
                        linkModel.deleteLink(link)
                        noteAndLinkModel.deleteNoteAndLink(
                            NoteAndLink(
                                noteUid = entity.dataEntity.uid,
                                linkId = link.id
                            )
                        )
                    }
                    entity.taskEntities.forEach {
                        // TODO: need finishing.
                    }
                }
            }
        }

        if (uiState.isOptionsDialog) {
            OptionsDialog(
                data = uiState.selectedNote,
                dataStoreModel = datastoreModel,
                dataModel = dataModel,
                mainModel = mainModel,
                linkModel = linkModel,
                noteAndLinkModel = noteAndLinkModel
            )
        }

        ModalNavigationDrawer(
            drawerContent = {
                NavigationDrawer(
                    dataStoreModel = datastoreModel,
                    tagModel = tagModel,
                    drawerState = drawerState,
                    homeScreen = mainModel
                )
            },
            drawerState = drawerState
        ) {
            androidx.compose.material.Scaffold(
                modifier = Modifier
                    .navigationBarsPadding()
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                scaffoldState = scaffoldState,
                backgroundColor = MaterialTheme.colorScheme.surface,
                topBar = {
                    when {
                        uiState.isHomeScreen && uiState.isSelection -> {
                            HomeSelectionTopAppBar(
                                datastoreModel = datastoreModel,
                                mainModel = mainModel,
                                dataModel = dataModel,
                                notificationModel = notificationModel,
                                noteAndTagModel = noteAndTagModel,
                                tagModel = tagModel,
                                taskModel = taskModel,
                                noteAndTaskModel = noteAndTaskModel,
                                undo = undo
                            )
                        }

                        !uiState.isHomeScreen && uiState.isSelection -> {
                            RemovedSelectionTopAppBar(
                                datastoreModel = datastoreModel,
                                mainModel = mainModel,
                                dataModel = dataModel
                            )
                        }

                        else -> {
                            MainTopAppBar(
                                datastoreModel = datastoreModel,
                                mainModel = mainModel,
                                scrollBehavior = scrollBehavior,
                                drawerState = drawerState
                            )
                        }
                    }
                },
                floatingActionButton = {
                    if (uiState.isHomeScreen) {
                        ExtendedFloatingActionButton(
                            text = {
                                Text("Compose")
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(id = CommonIcons.PLUS_ICON),
                                    null
                                )
                            },
                            onClick = {
                                sound.makeSound.invoke(
                                    context,
                                    CommonConstants.KEY_STANDARD,
                                    thereIsSoundEffect.value
                                )
                                navigator.push(
                                    WorkplaceScreen(
                                        backgroundColor = getBackgroundColor,
                                        textColor = getTextColor
                                    )
                                )
                            },
                            expanded = scrollBehavior.state.collapsedFraction != 1f,
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = contentColorFor(
                                backgroundColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        )
                    }
                }
            ) {
                Box(
                    contentAlignment = Alignment.TopCenter,
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (currentLayout.value == CommonConstants.LIST) {
                        LazyColumn(
                            state = listLayoutState,
                            modifier = Modifier
                                .animateContentSize()
                                .fillMaxSize(),
                        ) {
                            items(
                                items = filteredObserverLocalNotes,
                                key = { it.dataEntity.uid }
                            ) { entity ->
                                NoteCard(
                                    dataStoreModel = datastoreModel,
                                    taskModel = taskModel,
                                    noteAndTaskModel = noteAndTaskModel,
                                    audioModel = audioModel,
                                    noteAndAudioModel = noteAndAudioModel,
                                    linkModel = linkModel,
                                    noteAndLinkModel = noteAndLinkModel,
                                    homeModel = mainModel,
                                    isHomeScreen = uiState.isHomeScreen,
                                    noteEntity = entity,
                                    mediaModel = mediaModel,
                                    noteAndMediaModel = noteAndMediaModel
                                ) {
                                    dataModel.editData(it.dataEntity.copy(removed = 1))
                                    // to cancel the alarm manager reminding.
                                    notificationModel.scheduleNotification(
                                        context = context,
                                        dateTime = it.dataEntity.reminding,
                                        title = it.dataEntity.title,
                                        message = it.dataEntity.description,
                                        uid = it.dataEntity.uid,
                                        onReset = { true }
                                    )

                                    undo.invoke(entity.dataEntity)
                                }
                            }
                        }
                    } else {
                        LazyVerticalStaggeredGrid(
                            modifier = Modifier
                                .animateContentSize()
                                .fillMaxSize(),
                            columns = StaggeredGridCells.Fixed(2),
                            state = gridLayoutState
                        ) {
                            items(
                                items = filteredObserverLocalNotes,
                                key = { it.dataEntity.uid }
                            ) { entity ->
                                NoteCard(
                                    dataStoreModel = datastoreModel,
                                    taskModel = taskModel,
                                    noteAndTaskModel = noteAndTaskModel,
                                    audioModel = audioModel,
                                    noteAndAudioModel = noteAndAudioModel,
                                    linkModel = linkModel,
                                    noteAndLinkModel = noteAndLinkModel,
                                    homeModel = mainModel,
                                    isHomeScreen = uiState.isHomeScreen,
                                    noteEntity = entity,
                                    mediaModel = mediaModel,
                                    noteAndMediaModel = noteAndMediaModel
                                ) {

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}