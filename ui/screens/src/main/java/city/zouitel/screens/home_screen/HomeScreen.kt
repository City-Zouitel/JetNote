@file:SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter", "UnrememberedMutableState")

package city.zouitel.screens.home_screen

import android.annotation.SuppressLint
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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
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
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import city.zouitel.screens.navigation_drawer.NavigationDrawer
import city.zouitel.screens.navigation_drawer.Screens
import city.zouitel.screens.note_card.NoteCard
import city.zouitel.screens.sound
import city.zouitel.screens.top_action_bar.NoteTopAppBar
import city.zouitel.screens.top_action_bar.selection_bars.HomeSelectionTopAppBar
import city.zouitel.note.DataScreenModel
import city.zouitel.note.model.Data
import city.zouitel.note.model.Note
import city.zouitel.note.ui.add_screen.AddScreen
import city.zouitel.notifications.viewmodel.NotificationVM
import city.zouitel.systemDesign.Cons.BY_NAME
import city.zouitel.systemDesign.Cons.KEY_STANDARD
import city.zouitel.systemDesign.Cons.LIST
import city.zouitel.systemDesign.Cons.ORDER_BY_NEWEST
import city.zouitel.systemDesign.Cons.ORDER_BY_OLDEST
import city.zouitel.systemDesign.Cons.ORDER_BY_PRIORITY
import city.zouitel.systemDesign.Cons.ORDER_BY_REMINDER
import city.zouitel.systemDesign.Cons.SEARCH_IN_LOCAL
import city.zouitel.systemDesign.DataStoreVM
import city.zouitel.systemDesign.Icons.PLUS_ICON
import city.zouitel.tags.model.Tag
import city.zouitel.tags.viewmodel.NoteAndTagScreenModel
import city.zouitel.tags.viewmodel.TagScreenModel
import city.zouitel.tasks.viewmodel.NoteAndTaskScreenModel
import city.zouitel.tasks.viewmodel.TaskScreenModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.UUID

class HomeScreen: Screen, KoinComponent {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val dataStoreVM: DataStoreVM by inject()
        val notificationVM: NotificationVM by inject()

        val context = LocalContext.current
        val navigator = LocalNavigator.currentOrThrow

        val searchTitleState = remember { mutableStateOf("") }
        val searchTagEntityState = remember { mutableStateOf(Tag()) }
        val currentLayout = remember(dataStoreVM, dataStoreVM::getLayout).collectAsState()
        val thereIsSoundEffect = remember(dataStoreVM, dataStoreVM::getSound).collectAsState()
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scaffoldState = rememberScaffoldState()

        val tagModel = getScreenModel<TagScreenModel>()
        val noteAndTagModel = getScreenModel<NoteAndTagScreenModel>()
        val taskModel = getScreenModel<TaskScreenModel>()
        val noteAndTaskModel = getScreenModel<NoteAndTaskScreenModel>()
        val homeModel = getScreenModel<HomeScreenModel>()
        val dataModel = getScreenModel<DataScreenModel>()

        // to observer notes while changing immediately.
        val observerLocalNotes: State<List<Note>> = when (
            remember(dataStoreVM, dataStoreVM::getOrdination).collectAsState().value
        ) {
            BY_NAME -> homeModel.allNotesById.collectAsState()
            ORDER_BY_OLDEST -> homeModel.allNotesByOldest.collectAsState()
            ORDER_BY_NEWEST -> homeModel.allNotesByNewest.collectAsState()
            ORDER_BY_PRIORITY -> homeModel.allNotesByPriority.collectAsState()
            ORDER_BY_REMINDER -> homeModel.allRemindingNotes.collectAsState()
            else -> homeModel.allNotesById.collectAsState()
        }

        val filteredObserverLocalNotes by remember(observerLocalNotes.value) {
            derivedStateOf {
                observerLocalNotes.value.filter {
                    it.dataEntity.title?.contains(
                        searchTitleState.value,
                        true
                    ) ?: true || it.tagEntities.contains(searchTagEntityState.value)
                }
            }
        }

        val uid by lazy { UUID.randomUUID().toString() }
        val topAppBarState = rememberTopAppBarState()
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
        val listLayoutState = rememberLazyListState()
        val gridLayoutState = rememberLazyStaggeredGridState()
        val coroutineScope = rememberCoroutineScope()

        val removedNotesState = homeModel.allTrashedNotes.collectAsState()
        val isProcessing = homeModel.isProcessing

        val expandedSortMenuState = remember { mutableStateOf(false) }

        val pullRefreshState = rememberPullRefreshState(
            refreshing = isProcessing,
            onRefresh = {
                navigator.push(this)
            }
        )

        val homeSelectionState = remember { mutableStateOf(false) }
        val selectedNotes = remember { mutableStateListOf<Data>() }

        //undo snack-bar.
        val undo = UndoSnackbar(
            viewModule = dataModel,
            scaffoldState = scaffoldState,
            scope = coroutineScope,
            trashedNotesState = removedNotesState
        )

        ModalNavigationDrawer(
            drawerContent = {
                NavigationDrawer(
                    tagModel = tagModel,
                    drawerState = drawerState,
                    searchTagEntity = searchTagEntityState,
                    searchTitle = searchTitleState
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
                    if (homeSelectionState.value) {
                        HomeSelectionTopAppBar(
                            dataModel = dataModel,
                            tagModel = tagModel,
                            noteAndTagModel = noteAndTagModel,
                            taskModel = taskModel,
                            noteAndTaskModel = noteAndTaskModel,
                            homeSelectionState = homeSelectionState,
                            selectedNotes = selectedNotes,
                            undo = undo
                        )
                    } else {
                        NoteTopAppBar(
                            searchNoteTitle = searchTitleState,
                            scrollBehavior = scrollBehavior,
                            drawerState = drawerState,
                            thisHomeScreen = true,
                            confirmationDialogState = null,
                            expandedSortMenuState = expandedSortMenuState,
                            searchScreen = SEARCH_IN_LOCAL,
                            tagEntity = searchTagEntityState
                        )
                    }
                },
                floatingActionButton = {
                    ExtendedFloatingActionButton(
                        text = {
                            Text("Compose")
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = PLUS_ICON),
                                null
                            )
                        },
                        onClick = {
                            sound.makeSound.invoke(context, KEY_STANDARD, thereIsSoundEffect.value)
                            navigator.push(AddScreen(uid, null))
                        },
                        expanded = scrollBehavior.state.collapsedFraction != 1f,
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = contentColorFor(
                            backgroundColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                }
            ) {
                Box(
                    contentAlignment = Alignment.TopCenter,
                    modifier = Modifier
                        .fillMaxSize()
                        .pullRefresh(state = pullRefreshState)
                ) {
                    if (currentLayout.value == LIST) {
                        LazyColumn(
                            state = listLayoutState,
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            items(
                                items = filteredObserverLocalNotes,
                                key = { it.dataEntity.uid }
                            ) { entity ->
                                NoteCard(
                                    taskModel = taskModel,
                                    noteAndTaskModel = noteAndTaskModel,
                                    dataModel = dataModel,
                                    screen = Screens.HOME_SCREEN,
                                    noteEntity = entity,
                                    homeSelectionState = homeSelectionState,
                                    trashSelectionState = null,
                                    selectedNotes = selectedNotes
                                ) {
                                    dataModel.editData(
                                        Data(
                                            title = it.dataEntity.title,
                                            description = it.dataEntity.description,
                                            priority = it.dataEntity.priority,
                                            uid = it.dataEntity.uid,
                                            color = it.dataEntity.color,
                                            textColor = it.dataEntity.textColor,
                                            trashed = 1
                                        )
                                    )
                                    undo.invoke(entity.dataEntity)
                                }
                            }
                        }
                    } else {
                        LazyVerticalStaggeredGrid(
                            modifier = Modifier.fillMaxSize(),
                            columns = StaggeredGridCells.Fixed(2),
                            state = gridLayoutState
                        ) {
                            items(
                                items = filteredObserverLocalNotes,
                                key = { it.dataEntity.uid }
                            ) { entity ->
                                NoteCard(
                                    taskModel = taskModel,
                                    noteAndTaskModel = noteAndTaskModel,
                                    dataModel = dataModel,
                                    screen = Screens.HOME_SCREEN,
                                    noteEntity = entity,
                                    homeSelectionState = homeSelectionState,
                                    trashSelectionState = null,
                                    selectedNotes = selectedNotes
                                ) {
                                    dataModel.editData(
                                        Data(
                                            title = it.dataEntity.title,
                                            description = it.dataEntity.description,
                                            priority = it.dataEntity.priority,
                                            uid = it.dataEntity.uid,
                                            color = it.dataEntity.color,
                                            textColor = it.dataEntity.textColor,
                                            trashed = 1
                                        )
                                    )

                                    // to cancel the alarm manager reminding.
                                    notificationVM.scheduleNotification(
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
                    }

                    PullRefreshIndicator(
                        refreshing = dataModel.isProcessing,
                        state = pullRefreshState,
                    )
                }
            }
        }
    }
}
