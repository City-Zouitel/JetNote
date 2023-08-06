package com.example.graph.home_screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withStarted
import androidx.navigation.NavController
import city.zouitel.api.ApiResult
import city.zouitel.api.ApiViewModel
import city.zouitel.network.AppNetworkState
import city.zouitel.api.Invalid
import city.zouitel.network.NetworkMonitor
import com.example.common_ui.Cons.ADD_ROUTE
import com.example.common_ui.Cons.HOME_ROUTE
import com.example.common_ui.Cons.KEY_STANDARD
import com.example.common_ui.Cons.NUL
import com.example.common_ui.Cons.BY_NAME
import com.example.common_ui.Cons.ORDER_BY_NEWEST
import com.example.common_ui.Cons.ORDER_BY_OLDEST
import com.example.common_ui.Cons.ORDER_BY_PRIORITY
import com.example.common_ui.Cons.ORDER_BY_REMINDER
import com.example.common_ui.Cons.NOTES_PLACEHOLDER
import com.example.common_ui.Icons.PLUS_ICON
import com.example.common_ui.MaterialColors.Companion.SURFACE
import com.example.common_ui.MaterialColors.Companion.SURFACE_VARIANT
import com.example.common_ui.VerticalGrid
import com.example.graph.sound
import com.example.common_ui.DataStoreVM
import com.example.graph.getMaterialColor
import com.example.graph.navigation_drawer.NavigationDrawer
import com.example.graph.Screens
import com.example.graph.note_card.NoteCard
import com.example.graph.top_action_bar.NoteTopAppBar
import com.example.graph.top_action_bar.selection_bars.HomeSelectionTopAppBar
import com.example.note.NoteViewModel
import com.example.note.DataViewModel
import com.example.note.model.Data
import com.example.note.model.Note
import com.example.tags.model.Tag
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.*

@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter",
    "UnusedMaterialScaffoldPaddingParameter",
    "UnrememberedMutableState", "CoroutineCreationDuringComposition"
)
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterialApi::class,
)
@Composable
fun NoteHome(
    dataViewModel: DataViewModel = hiltViewModel(),
    entityVM: NoteViewModel = hiltViewModel(),
    dataStoreVM: DataStoreVM = hiltViewModel(),
    apiViewModel: ApiViewModel = hiltViewModel(),
    networkMonitor: NetworkMonitor,
    appNetState: AppNetworkState = rememberNetworkState(networkMonitor = networkMonitor),
    navController: NavController,
) {
    val ctx = LocalContext.current
    val life = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()

//    scope.launch {
//        life.lifecycle.withStarted {
//            println("*************************")
//        }
//    }

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

    //  to observer notes while changing immediately.
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

    val uid = UUID.randomUUID().toString()
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val trashedNotesState = remember(entityVM) { entityVM.allTrashedNotes }.collectAsState()

    val expandedSortMenuState = remember { mutableStateOf(false) }

    val api_state by remember(apiViewModel, apiViewModel::uiState).collectAsState()

    val isOffline by appNetState.isOnline.collectAsState()
    //
    LaunchedEffect(key1 = isOffline) {
        if (isOffline) {
            Toast.makeText(ctx, "you're online.", Toast.LENGTH_SHORT).show()
        }
    }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = dataViewModel.isProcessing,
        onRefresh = {

            when(api_state) {
                is ApiResult.Error -> {
                    Toast.makeText(
                        ctx,
                        (api_state as? ApiResult.Error)?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                ApiResult.Loading -> {}
                is ApiResult.Success -> {
                    ((api_state as? ApiResult.Success)?.data as? List<Invalid>)?.forEach(::println)
                }
            }

            navController.navigate(HOME_ROUTE)
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
                        searchScreen = NOTES_PLACEHOLDER,
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

                        navController.navigate("$ADD_ROUTE/$uid/$NUL")
                    },
                    expanded = scrollBehavior.state.collapsedFraction != 1f,
                    containerColor = getMaterialColor(SURFACE_VARIANT),
                    contentColor = contentColorFor(
                        backgroundColor = getMaterialColor(SURFACE_VARIANT)
                    ),
                    modifier = Modifier.navigationBarsPadding()
                )
            }
        ) {
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(state = pullRefreshState)
            ) {
                if (currentLayout.value == "LIST") {
                    LazyColumn(
                        state = lazyListState,
                        modifier = Modifier
                            .fillMaxSize(),
                    ) {
                        items(
                            observerLocalNotes.value.filter {
                                it.dataEntity.title?.contains(searchTitleState.value, true) ?: true ||
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
                } else {
                    LazyColumn(
                        state = lazyListState,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        item {
                            VerticalGrid(
                                maxColumnWidth = 220.dp
                            ) {
                                observerLocalNotes.value.filter {
                                    it.dataEntity.title?.contains(searchTitleState.value, true) ?: true ||
                                            it.tagEntities.contains(searchTagEntityState.value)
                                }.forEach { entity ->
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