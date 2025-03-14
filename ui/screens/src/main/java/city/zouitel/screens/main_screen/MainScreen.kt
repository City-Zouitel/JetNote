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
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import city.zouitel.audio.ui.component.AudioScreenModel
import city.zouitel.domain.provider.SharedScreen
import city.zouitel.domain.utils.Action
import city.zouitel.links.ui.LinkScreenModel
import city.zouitel.media.ui.MediaScreenModel
import city.zouitel.note.ui.DataScreenModel
import city.zouitel.screens.main_screen.utils.ArchiveSelectionTopAppBar
import city.zouitel.screens.main_screen.utils.HomeSelectionTopAppBar
import city.zouitel.screens.main_screen.utils.MainTopAppBar
import city.zouitel.screens.main_screen.utils.undoSnackbar
import city.zouitel.screens.navigation_drawer.NavigationDrawer
import city.zouitel.screens.note_card.NoteCard
import city.zouitel.screens.utils.sound
import city.zouitel.systemDesign.CommonConstants
import city.zouitel.systemDesign.CommonConstants.NAME_ORDER
import city.zouitel.systemDesign.CommonConstants.NEWEST_ORDER
import city.zouitel.systemDesign.CommonConstants.OLDEST_ORDER
import city.zouitel.systemDesign.CommonConstants.PRIORITY_ORDER
import city.zouitel.systemDesign.CommonConstants.REMINDING_ORDER
import city.zouitel.systemDesign.CommonIcons
import city.zouitel.systemDesign.DataStoreScreenModel
import city.zouitel.tags.ui.NoteAndTagScreenModel
import city.zouitel.tags.ui.TagScreenModel
import city.zouitel.tasks.ui.TaskScreenModel
import com.skydoves.cloudy.cloudy
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class MainScreen(val isHome: Boolean = true): Screen {

    @Composable
    override fun Content() {
        val mainModel = getScreenModel<MainScreenModel>()

        LaunchedEffect(isHome) {
            mainModel.updateScreen(isHome)
        }

        Main(
            mainModel = mainModel,
            tagModel = getScreenModel(),
            noteAndTagModel = getScreenModel(),
            taskModel = getScreenModel(),
            dataModel = getScreenModel(),
            audioModel = getScreenModel(),
            linkModel = getScreenModel(),
            datastoreModel = getScreenModel(),
            mediaModel = getScreenModel()
        )
    }

    @SuppressLint(
        "UnusedMaterialScaffoldPaddingParameter",
        "UnusedMaterial3ScaffoldPaddingParameter"
    )
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalUuidApi::class)
    @Composable
    private fun Main(
        mainModel: MainScreenModel,
        datastoreModel: DataStoreScreenModel,
        tagModel: TagScreenModel,
        noteAndTagModel: NoteAndTagScreenModel,
        taskModel: TaskScreenModel,
        dataModel: DataScreenModel,
        audioModel: AudioScreenModel,
        linkModel: LinkScreenModel,
        mediaModel: MediaScreenModel
    ) {
        val context = LocalContext.current
        val navigator = LocalNavigator.currentOrThrow
        val navBottomSheet = LocalBottomSheetNavigator.current

        val uiState by remember(mainModel, mainModel::uiState).collectAsState()
        val currentLayout = remember(datastoreModel, datastoreModel::getLayout).collectAsState()
        val thereIsSoundEffect = remember(datastoreModel, datastoreModel::isMute).collectAsState()
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scaffoldState = rememberScaffoldState()

        // to observer notes while changing immediately.
        val observerNotes = if (uiState.isHomeScreen) {
            when (
                remember(datastoreModel, datastoreModel::getOrdination).collectAsState().value
            ) {
                NAME_ORDER -> mainModel.observeNames.collectAsState()
                OLDEST_ORDER -> mainModel.observeOldest.collectAsState()
                NEWEST_ORDER -> mainModel.observeNewest.collectAsState()
                PRIORITY_ORDER -> mainModel.observePriorities.collectAsState()
                REMINDING_ORDER -> mainModel.observeArchives.collectAsState()
                else -> mainModel.observeDefaults.collectAsState()
            }
        } else {
            mainModel.observeArchives.collectAsState()
        }

        val observerArchivedNotes = remember(mainModel, mainModel::observeArchives).collectAsState()

        val filteredObserverLocalNotes by remember(observerNotes.value) {
            derivedStateOf {
                observerNotes.value.filter {
                    it.data.title.contains(uiState.searchTitle, true) || it.tags.contains(uiState.searchTag)
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

        val workplaceScreen = rememberScreen(
            SharedScreen.Workplace(
                Uuid.random().toString(),
                true,
                null,
                null,
                MaterialTheme.colorScheme.surface.toArgb(),
                MaterialTheme.colorScheme.onSurface.toArgb(),
                0
            )
        )

        //undo snack-bar.
        val undo = undoSnackbar(
            dataModule = dataModel,
            scaffoldState = scaffoldState,
            scope = coroutineScope,
            archivedNotesState = observerArchivedNotes
        )

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                NavigationDrawer(
                    dataStoreModel = datastoreModel,
                    tagModel = tagModel,
                    drawerState = drawerState,
                    mainModel = mainModel
                )
            }
        ) {
            androidx.compose.material.Scaffold(
                modifier = Modifier
                    .navigationBarsPadding()
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
                    .cloudy(enabled = navBottomSheet.isVisible),
                scaffoldState = scaffoldState,
                backgroundColor = MaterialTheme.colorScheme.surface,
                topBar = {
                    when {
                        uiState.isHomeScreen && uiState.isSelection -> {
                            HomeSelectionTopAppBar(
                                datastoreModel = datastoreModel,
                                mainModel = mainModel,
                                dataModel = dataModel,
                                noteAndTagModel = noteAndTagModel,
                                tagModel = tagModel,
                                taskModel = taskModel,
                                undo = undo
                            )
                        }

                        !uiState.isHomeScreen && uiState.isSelection -> {
                            ArchiveSelectionTopAppBar(
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
                                Icon(painterResource(CommonIcons.PLUS_ICON), null)
                            },
                            onClick = {
                                sound.performSoundEffect(context, CommonConstants.KEY_STANDARD, thereIsSoundEffect.value)
                                navigator.push(workplaceScreen)
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
                                key = { it.data.uid }
                            ) { entity ->
                                NoteCard(
                                    dataStoreModel = datastoreModel,
                                    taskModel = taskModel,
                                    audioModel = audioModel,
                                    linkModel = linkModel,
                                    homeModel = mainModel,
                                    isHomeScreen = uiState.isHomeScreen,
                                    noteEntity = entity,
                                    mediaModel = mediaModel,
                                ) {
                                    dataModel.sendAction(Action.Archive(it.data.uid))
                                    // to.do cancel the alarm manager reminder.
                                    undo.invoke(entity.data)
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
                                key = { it.data.uid }
                            ) { entity ->
                                NoteCard(
                                    dataStoreModel = datastoreModel,
                                    taskModel = taskModel,
                                    audioModel = audioModel,
                                    linkModel = linkModel,
                                    homeModel = mainModel,
                                    isHomeScreen = uiState.isHomeScreen,
                                    noteEntity = entity,
                                    mediaModel = mediaModel,
                                ) {}
                            }
                        }
                    }
                }
            }
        }
    }
}