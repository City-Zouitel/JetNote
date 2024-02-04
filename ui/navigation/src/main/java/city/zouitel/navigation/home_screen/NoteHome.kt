package city.zouitel.navigation.home_screen

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
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import city.zouitel.navigation.getMaterialColor
import city.zouitel.navigation.navigation_drawer.NavigationDrawer
import city.zouitel.navigation.navigation_drawer.Screens
import city.zouitel.navigation.note_card.NoteCard
import city.zouitel.navigation.sound
import city.zouitel.navigation.top_action_bar.NoteTopAppBar
import city.zouitel.navigation.top_action_bar.selection_bars.HomeSelectionTopAppBar
import city.zouitel.note.DataViewModel
import city.zouitel.note.NoteViewModel
import city.zouitel.note.model.Data
import city.zouitel.note.model.Note
import city.zouitel.systemDesign.Cons.ADD_ROUTE
import city.zouitel.systemDesign.Cons.BY_NAME
import city.zouitel.systemDesign.Cons.HOME_ROUTE
import city.zouitel.systemDesign.Cons.KEY_STANDARD
import city.zouitel.systemDesign.Cons.LIST
import city.zouitel.systemDesign.Cons.NUL
import city.zouitel.systemDesign.Cons.ORDER_BY_NEWEST
import city.zouitel.systemDesign.Cons.ORDER_BY_OLDEST
import city.zouitel.systemDesign.Cons.ORDER_BY_PRIORITY
import city.zouitel.systemDesign.Cons.ORDER_BY_REMINDER
import city.zouitel.systemDesign.Cons.SEARCH_IN_LOCAL
import city.zouitel.systemDesign.DataStoreVM
import city.zouitel.systemDesign.Icons.PLUS_ICON
import city.zouitel.systemDesign.MaterialColors.Companion.SURFACE
import city.zouitel.systemDesign.MaterialColors.Companion.SURFACE_VARIANT
import city.zouitel.tags.model.Tag
import org.koin.androidx.compose.koinViewModel
import java.util.UUID

@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter",
    "UnusedMaterialScaffoldPaddingParameter",
    "UnrememberedMutableState"
)
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterialApi::class,
)
@Composable
fun NoteHome(
    dataViewModel: DataViewModel = koinViewModel(),
    entityVM: NoteViewModel = koinViewModel(),
    dataStoreVM: DataStoreVM = koinViewModel(),
    navController: NavController,
) {
    val sldif = city.zouitel.navigation.State.Sound(dataStoreVM)
    val sdo = sldif.soundState.invoke()

    val ctx = LocalContext.current
    //
    val searchTitleState = remember { mutableStateOf("") }
    val searchTagEntityState = remember { mutableStateOf(Tag()) }

    //
    val currentLayout = remember(dataStoreVM, dataStoreVM::getLayout).collectAsState()
    //
    val thereIsSoundEffect = remember(dataStoreVM, dataStoreVM::getSound).collectAsState()

    //
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    //
    val scaffoldState = rememberScaffoldState()

    // to observer notes while changing immediately.
    val observerLocalNotes: State<List<Note>> = when (
        remember(dataStoreVM, dataStoreVM::getOrdination).collectAsState().value
    ) {
        BY_NAME -> remember(entityVM, entityVM::allNotesByName).collectAsState()
        ORDER_BY_OLDEST -> remember(entityVM, entityVM::allNotesByOldest).collectAsState()
        ORDER_BY_NEWEST -> remember(entityVM, entityVM::allNotesByNewest).collectAsState()
        ORDER_BY_PRIORITY -> remember(entityVM, entityVM::allNotesByPriority).collectAsState()
        ORDER_BY_REMINDER -> remember(entityVM, entityVM::allRemindingNotes).collectAsState()
        else -> remember(entityVM, entityVM::allNotesById).collectAsState()
    }

    val uid by lazy { UUID.randomUUID().toString() }
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
    val listLayoutState = rememberLazyListState()
    val gridLayoutState = rememberLazyStaggeredGridState()
    val coroutineScope = rememberCoroutineScope()

    val trashedNotesState = remember(entityVM) { entityVM.allTrashedNotes }.collectAsState()
    val isProcessing = remember(dataViewModel, dataViewModel::isProcessing)

    val expandedSortMenuState = remember { mutableStateOf(false) }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isProcessing,
        onRefresh = {
            navController.apply {
                navigate(HOME_ROUTE)
                popBackStack()
            }
        }
    )

    val homeSelectionState = remember { mutableStateOf(false) }
    val selectedNotes = remember { mutableStateListOf<Data>() }

    //undo snack-bar.
    val undo = UndoSnackbar(
        viewModule = dataViewModel,
        scaffoldState = scaffoldState,
        scope = coroutineScope,
        trashedNotesState = trashedNotesState
    )

    ModalNavigationDrawer(
        drawerContent = {
            NavigationDrawer(
                drawerState = drawerState,
                navController = navController,
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
            backgroundColor = getMaterialColor(SURFACE),
            topBar = {
                if (homeSelectionState.value) {
                    HomeSelectionTopAppBar(
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
                        sound.makeSound.invoke(ctx, KEY_STANDARD, thereIsSoundEffect.value)

                        with(navController) {
                            arrayOf(ADD_ROUTE, uid, NUL)
                                .joinToString("/")
                                .apply {
                                    navigate(this)
                                    clearBackStack(this)
                                }
                        }
                    },
                    expanded = scrollBehavior.state.collapsedFraction != 1f,
                    containerColor = getMaterialColor(SURFACE_VARIANT),
                    contentColor = contentColorFor(
                        backgroundColor = getMaterialColor(SURFACE_VARIANT)
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
                        modifier = Modifier
                            .fillMaxSize(),
                    ) {
                        items(
                            items = observerLocalNotes.value.filter {
                                it.dataEntity.title?.contains(
                                    searchTitleState.value,
                                    true
                                ) ?: true ||
                                        it.tagEntities.contains(searchTagEntityState.value)
                            },
                            key = {
                                it.dataEntity.uid
                            }
                        ) { entity ->
                            NoteCard(
                                screen = Screens.HOME_SCREEN,
                                noteEntity = entity,
                                navController = navController,
                                homeSelectionState = homeSelectionState,
                                trashSelectionState = null,
                                selectedNotes = selectedNotes
                            ) {
                                dataViewModel.editData(
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
                            key = { it.dataEntity.uid },
                            items = observerLocalNotes.value.filter {
                                it.dataEntity.title?.contains(
                                    searchTitleState.value,
                                    true
                                ) ?: true ||
                                        it.tagEntities.contains(searchTagEntityState.value)
                            }
                        ) { entity ->
                            NoteCard(
                                screen = Screens.HOME_SCREEN,
                                noteEntity = entity,
                                navController = navController,
                                homeSelectionState = homeSelectionState,
                                trashSelectionState = null,
                                selectedNotes = selectedNotes
                            ) {
                                dataViewModel.editData(
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
                }

                PullRefreshIndicator(
                    refreshing = dataViewModel.isProcessing,
                    state = pullRefreshState,
                )

            }
        }
    }
}