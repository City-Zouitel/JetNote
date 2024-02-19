package city.zouitel.navigation.deleted_screen

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
import androidx.navigation.NavController
import city.zouitel.navigation.navigation_drawer.NavigationDrawer
import city.zouitel.navigation.navigation_drawer.Screens.*
import city.zouitel.navigation.note_card.NoteCard
import city.zouitel.navigation.top_action_bar.NoteTopAppBar
import city.zouitel.navigation.top_action_bar.selection_bars.TrashSelectionTopAppBar
import city.zouitel.navigation.top_action_bar.dialogs.EraseDialog
import city.zouitel.links.model.NoteAndLink
import city.zouitel.links.ui.LinkVM
import city.zouitel.links.ui.NoteAndLinkVM
import city.zouitel.note.NoteViewModel
import city.zouitel.note.DataViewModel
import city.zouitel.note.model.Data
import city.zouitel.note.model.Note
import city.zouitel.systemDesign.Cons.REC_DIR
import city.zouitel.systemDesign.Cons.IMG_DIR
import city.zouitel.systemDesign.Cons.SEARCH_IN_DELETED
import city.zouitel.systemDesign.DataStoreVM
import city.zouitel.systemDesign.MaterialColors.Companion.SURFACE
import city.zouitel.systemDesign.VerticalGrid
import city.zouitel.tags.model.Tag
import city.zouitel.tasks.viewmodel.NoteAndTaskViewModel
import city.zouitel.tasks.viewmodel.TaskViewModel
import city.zouitel.navigation.getMaterialColor
import city.zouitel.systemDesign.Cons.JPEG
import city.zouitel.systemDesign.Cons.LIST
import city.zouitel.systemDesign.Cons.MP3
import org.koin.androidx.compose.koinViewModel
import java.io.File

@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter",
    "UnusedMaterialScaffoldPaddingParameter"
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrashScreen(
    viewModule: DataViewModel = koinViewModel(),
    entityVM: NoteViewModel = koinViewModel(),
    dataStoreVM: DataStoreVM = koinViewModel(),
    linkVM: LinkVM = koinViewModel(),
    noteAndLinkVM: NoteAndLinkVM = koinViewModel(),
    taskViewModel: TaskViewModel = koinViewModel(),
    noteAndTodoVM: NoteAndTaskViewModel = koinViewModel(),
    navController: NavController,
) {
    val ctx = LocalContext.current
    val searchTitleState = remember { mutableStateOf("") }//.filterBadWords()

    val currentLayout = remember(dataStoreVM, dataStoreVM::getLayout).collectAsState()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scaffoldState = rememberScaffoldState()
    val observerTrashedNotes: State<List<Note>> =
        remember(entityVM, entityVM::allTrashedNotes).collectAsState()
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
    val confirmationDialogState = remember { mutableStateOf(false) }

    val trashSelectionState = remember { mutableStateOf(false) }
    val selectedNotes = remember { mutableStateListOf<Data>() }

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
                        searchScreen = SEARCH_IN_DELETED,
                        tagEntity = remember { mutableStateOf(Tag()) }
                    )
                }
            }
        ) {
            if (currentLayout.value == LIST) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(
                        observerTrashedNotes.value.filter { entity ->
                            entity.dataEntity.title?.contains(searchTitleState.value, true) ?: true
                        }
                    ) {entity ->
                        NoteCard(
                            screen = DELETED_SCREEN,
                            noteEntity = entity,
                            navController = navController,
                            homeSelectionState = null,
                            trashSelectionState = trashSelectionState,
                            selectedNotes = selectedNotes
                        ) {
                            viewModule.deleteData(Data(uid = entity.dataEntity.uid))
                            entity.linkEntities.forEach { link ->
                                linkVM.deleteLink(link)
                            }
                            entity.dataEntity.uid.let { _uid ->
                                File(ctx.filesDir.path + File.pathSeparator + IMG_DIR, "$_uid.$JPEG").delete()
                                File(ctx.filesDir.path + File.pathSeparator + REC_DIR, "$_uid$MP3").delete()
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
                                    screen = DELETED_SCREEN,
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