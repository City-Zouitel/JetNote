package com.example.jetnote.ui.home_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetnote.cons.*
import com.example.jetnote.cons.Screens.HOME_SCREEN
import com.example.jetnote.db.entities.Entity
import com.example.jetnote.db.entities.label.Label
import com.example.jetnote.db.entities.note.Note
import com.example.jetnote.ds.DataStore
import com.example.jetnote.fp.getMaterialColor
import com.example.jetnote.icons.PLUS_ICON
import com.example.jetnote.ui.layouts.VerticalGrid
import com.example.jetnote.ui.navigation_drawer.NavigationDrawer
import com.example.jetnote.ui.note_card.NoteCard
import com.example.jetnote.ui.snackebars.UndoSnackbar
import com.example.jetnote.ui.top_action_bar.*
import com.example.jetnote.vm.*
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.util.*

@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter",
    "UnusedMaterialScaffoldPaddingParameter",
    "UnrememberedMutableState"
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteHome(
    noteVM: NoteVM = hiltViewModel(),
    entityVM: EntityVM = hiltViewModel(),
    navController: NavController,
) {
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()
    //
    val searchTitleState = remember { mutableStateOf("") }
    val searchLabelState = remember { mutableStateOf(Label()) }
    //
    val noteDataStore = DataStore(ctx)
    //
    val orderBy = noteDataStore.getOrder.collectAsState("").value
    // the true value is 'list' layout by default and false is 'grid'.
    val currentLayout = noteDataStore.getLayout.collectAsState(true).value
    //
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    //
    val scaffoldState = rememberScaffoldState()
//     to observer notes while changing immediately.
    val observerLocalNotes: State<List<Entity>> = when (orderBy) {
        ORDER_BY_NAME -> remember(entityVM, entityVM::allNotesByName).collectAsState()
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

    val signInDialogState = remember { mutableStateOf(false) }
    val revokeAccessState = remember { mutableStateOf(false) }
    val expandedState = remember { mutableStateOf(false) }
    val expandedSortMenuState = remember { mutableStateOf(false) }

    val refreshState = rememberSwipeRefreshState(noteVM.isProcessing)

    val selectionState = remember { mutableStateOf(false) }
    val selectedNotes = remember { mutableStateListOf<Note>() }

    //undo snackbar.
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
        Scaffold(
            modifier = Modifier
                .navigationBarsPadding()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            scaffoldState = scaffoldState,
            backgroundColor = getMaterialColor(SURFACE),
            topBar = {
                if (selectionState.value) {
                    SelectionTopAppBar(selectionState = selectionState, selectedNotes = selectedNotes)
                } else {
                    NoteTopAppBar(
                        searchNoteTitle = searchTitleState,
                        dataStore = noteDataStore,
                        scrollBehavior = scrollBehavior,
                        drawerState = drawerState,
                        thisHomeScreen = true,
                        confirmationDialogState = null,
                        expandedSortMenuState = expandedSortMenuState,
                        expandedAccountMenuState = expandedState,
                        signInDialogState = signInDialogState,
                        revokeAccessDialogState = revokeAccessState,
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
                        navController.navigate("$ADD_ROUTE/$uid")
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
            SwipeRefresh(modifier = Modifier.fillMaxSize(),
                state = refreshState, onRefresh = {}) {
                if (currentLayout) {
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
            }
        }
    }
}