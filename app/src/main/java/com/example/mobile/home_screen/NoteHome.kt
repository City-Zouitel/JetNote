package com.example.mobile.home_screen

import android.annotation.SuppressLint
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.common_ui.Cons.ADD_ROUTE
import com.example.common_ui.Cons.HOME_ROUTE
import com.example.common_ui.Cons.KEY_STANDARD
import com.example.common_ui.Cons.NUL
import com.example.common_ui.Cons.BY_NAME
import com.example.common_ui.Cons.ORDER_BY_NEWEST
import com.example.common_ui.Cons.ORDER_BY_OLDEST
import com.example.common_ui.Cons.ORDER_BY_PRIORITY
import com.example.common_ui.Cons.ORDER_BY_REMINDER
import com.example.common_ui.Cons.SEARCH_IN_LOCAL
import com.example.common_ui.Icons.PLUS_ICON
import com.example.common_ui.MaterialColors.Companion.SURFACE
import com.example.common_ui.MaterialColors.Companion.SURFACE_VARIANT
import com.example.common_ui.VerticalGrid
import com.example.mobile.getMaterialColor
import com.example.mobile.navigation_drawer.NavigationDrawer
import com.example.mobile.navigation_drawer.Screens.HOME_SCREEN
import com.example.mobile.note_card.NoteCard
import com.example.graph.sound
import com.example.mobile.top_action_bar.NoteTopAppBar
import com.example.mobile.top_action_bar.SelectionTopAppBar
import com.example.local.model.Entity
import com.example.local.model.Label
import com.example.local.model.Note
import com.example.common_ui.DataStoreVM
import com.example.note.EntityVM
import com.example.note.NoteVM
import java.util.*

@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter",
    "UnusedMaterialScaffoldPaddingParameter",
    "UnrememberedMutableState"
)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun NoteHome(
    noteVM: NoteVM = hiltViewModel(),
    entityVM: EntityVM = hiltViewModel(),
    dataStoreVM: DataStoreVM = hiltViewModel(),
    navController: NavController,
) {
    val ctx = LocalContext.current
    //
    val searchTitleState = remember { mutableStateOf("") }//.filterBadWords()
    val searchLabelState = remember { mutableStateOf(Label()) }

    //
    val currentLayout = remember(dataStoreVM, dataStoreVM::getLayout).collectAsState()
    //
    val thereIsSoundEffect = remember(dataStoreVM, dataStoreVM::getSound).collectAsState()

    //
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    //
    val scaffoldState = rememberScaffoldState()
//     to observer notes while changing immediately.
    val observerLocalNotes: State<List<Entity>> = when (
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
    val isProcessing = remember(noteVM) { noteVM.isProcessing }

    val expandedSortMenuState = remember { mutableStateOf(false) }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = noteVM.isProcessing,
        onRefresh = {
            navController.navigate(HOME_ROUTE)
        }
    )

    val selectionState = remember { mutableStateOf(false) }
    val selectedNotes = remember { mutableStateListOf<Note>() }

    //undo snack-bar.
    val undo = UndoSnackbar(
        viewModule = noteVM,
        scaffoldState = scaffoldState,
        scope = coroutineScope,
        trashedNotesState = trashedNotesState
    )

    ModalNavigationDrawer(
        drawerContent = {
            NavigationDrawer(
                drawerState = drawerState,
                navController = navController,
                searchLabel = searchLabelState,
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
                if (selectionState.value) {
                    SelectionTopAppBar(
                        selectionState = selectionState,
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
                        label = searchLabelState
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
                                it.note.title?.contains(searchTitleState.value, true) ?: true ||
                                        it.labels.contains(searchLabelState.value)
                            }
                        ) { entity ->
                            NoteCard(
                                entity = entity,
                                navController = navController,
                                forScreen = HOME_SCREEN,
                                selectionState = selectionState,
                                selectedNotes = selectedNotes
                            ) {
                                noteVM.updateNote(
                                    Note(
                                        title = it.note.title,
                                        description = it.note.description,
                                        priority = it.note.priority,
                                        uid = it.note.uid,
                                        color = it.note.color,
                                        textColor = it.note.textColor,
                                        trashed = 1
                                    )
                                )
                                undo.invoke(entity.note)
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
                                    it.note.title?.contains(searchTitleState.value, true) ?: true ||
                                            it.labels.contains(searchLabelState.value)
                                }.forEach { entity ->
                                    NoteCard(
                                        entity = entity,
                                        navController = navController,
                                        forScreen = HOME_SCREEN,
                                        selectionState = selectionState,
                                        selectedNotes = selectedNotes
                                    ) {
                                        noteVM.updateNote(
                                            Note(
                                                title = it.note.title,
                                                description = it.note.description,
                                                priority = it.note.priority,
                                                uid = it.note.uid,
                                                color = it.note.color,
                                                textColor = it.note.textColor,
                                                trashed = 1
                                            )
                                        )
                                        undo.invoke(entity.note)
                                    }
                                }
                            }
                        }
                    }
                }

                PullRefreshIndicator(
                    refreshing = noteVM.isProcessing,
                    state = pullRefreshState,
                )

            }
        }
    }
}