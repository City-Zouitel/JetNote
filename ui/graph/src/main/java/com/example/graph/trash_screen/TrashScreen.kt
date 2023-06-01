package com.example.graph.trash_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.common_ui.Cons.AUDIOS
import com.example.common_ui.Cons.IMAGES
import com.example.common_ui.Cons.JPEG
import com.example.common_ui.Cons.MP3
import com.example.common_ui.Cons.SEARCH_IN_TRASH
import com.example.common_ui.DataStoreVM
import com.example.common_ui.MaterialColors.Companion.SURFACE
import com.example.common_ui.VerticalGrid
import com.example.graph.getMaterialColor
import com.example.graph.navigation_drawer.NavigationDrawer
import com.example.graph.navigation_drawer.Screens.*
import com.example.graph.note_card.NoteCard
import com.example.graph.top_action_bar.NoteTopAppBar
import com.example.graph.top_action_bar.TrashSelectionTopAppBar
import com.example.graph.top_action_bar.dialogs.EraseDialog
import com.example.links.ui.LinkVM
import com.example.links.ui.NoteAndLinkVM
import com.example.local.model.relational.NoteEntity
import com.example.local.model.TagEntity
import com.example.local.model.Note
import com.example.local.model.NoteAndLink
import com.example.note.NoteViewModel
import com.example.note.DataViewModel
import com.example.tasks.NoteAndTodoVM
import com.example.tasks.TaskViewModel
import java.io.File

@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter",
    "UnusedMaterialScaffoldPaddingParameter"
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrashScreen(
    viewModule: DataViewModel = hiltViewModel(),
    entityVM: NoteViewModel = hiltViewModel(),
    dataStoreVM: DataStoreVM = hiltViewModel(),
    linkVM: LinkVM = hiltViewModel(),
    noteAndLinkVM: NoteAndLinkVM = hiltViewModel(),
    taskViewModel: TaskViewModel = hiltViewModel(),
    noteAndTodoVM: NoteAndTodoVM = hiltViewModel(),
    navController: NavController,
) {
    val ctx = LocalContext.current
    val searchTitleState = remember { mutableStateOf("") }//.filterBadWords()

    val currentLayout = remember(dataStoreVM, dataStoreVM::getLayout).collectAsState()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scaffoldState = rememberScaffoldState()
    val observerTrashedNotes: State<List<NoteEntity>> =
        remember(entityVM, entityVM::allTrashedNotes).collectAsState()
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
    val confirmationDialogState = remember { mutableStateOf(false) }

    val trashSelectionState = remember { mutableStateOf(false) }
    val selectedNotes = remember { mutableStateListOf<Note>() }

    //
    if (confirmationDialogState.value) {
        EraseDialog(dialogState = confirmationDialogState) {
            viewModule.eraseTrash()
            observerTrashedNotes.value.forEach { entity ->
                entity.linkEntities.forEach { link ->
                    linkVM.deleteLink(link)
                    noteAndLinkVM.deleteNoteAndLink(
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

    ModalNavigationDrawer(
        drawerContent = {
            NavigationDrawer(
                drawerState = drawerState,
                navController = navController,
                searchTagEntity = null,
                searchTitle = searchTitleState
            )
        },
        drawerState = drawerState
    ) {
        androidx.compose.material.Scaffold(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .navigationBarsPadding(),
            scaffoldState = scaffoldState,
            backgroundColor = getMaterialColor(SURFACE),
            topBar = {
                if (trashSelectionState.value) {
                    TrashSelectionTopAppBar(
                        trashSelectionState = trashSelectionState,
                        selectedNotes = selectedNotes
                    )
                } else {
                    NoteTopAppBar(
                        searchNoteTitle = searchTitleState,
                        scrollBehavior = scrollBehavior,
                        drawerState = drawerState,
                        thisHomeScreen = false,
                        confirmationDialogState = confirmationDialogState,
                        expandedSortMenuState = null,
                        searchScreen = SEARCH_IN_TRASH,
                        tagEntity = remember { mutableStateOf(TagEntity()) }
                    )
                }
            }
        ) {
            if (currentLayout.value == "LIST") {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(
                        observerTrashedNotes.value.filter { entity ->
                            entity.dataEntity.title?.contains(searchTitleState.value, true) ?: true
                        }
                    ) {entity ->
                        NoteCard(
                            screen = TRASH_SCREEN,
                            noteEntity = entity,
                            navController = navController,
                            homeSelectionState = null,
                            trashSelectionState = trashSelectionState,
                            selectedNotes = selectedNotes
                        ) {
                            viewModule.deleteNote(Note(uid = entity.dataEntity.uid))
                            entity.linkEntities.forEach { link ->
                                linkVM.deleteLink(link)
                            }
                            entity.dataEntity.uid.let { _uid ->
                                File(ctx.filesDir.path + File.pathSeparator + IMAGES, "$_uid.$JPEG").delete()
                                File(ctx.filesDir.path + File.pathSeparator + AUDIOS, "$_uid$MP3").delete()
                            }
                        }
                    }
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        VerticalGrid(maxColumnWidth = 220.dp) {
                            observerTrashedNotes.value.filter { entity ->
                                entity.dataEntity.title?.contains(searchTitleState.value, true) ?: true
                            }.forEach { entity ->
                                NoteCard(
                                    screen = TRASH_SCREEN,
                                    noteEntity = entity,
                                    navController = navController,
                                    homeSelectionState = null,
                                    trashSelectionState = trashSelectionState,
                                    selectedNotes = selectedNotes
                                ){

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}