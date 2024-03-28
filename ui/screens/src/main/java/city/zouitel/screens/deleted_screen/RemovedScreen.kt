package city.zouitel.screens.deleted_screen

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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import city.zouitel.links.model.NoteAndLink
import city.zouitel.links.ui.LinkVM
import city.zouitel.links.ui.NoteAndLinkVM
import city.zouitel.screens.home_screen.HomeScreenModel
import city.zouitel.note.DataScreenModel
import city.zouitel.note.model.Data
import city.zouitel.systemDesign.Cons.REC_DIR
import city.zouitel.systemDesign.Cons.IMG_DIR
import city.zouitel.systemDesign.Cons.SEARCH_IN_DELETED
import city.zouitel.systemDesign.DataStoreVM
import city.zouitel.systemDesign.VerticalGrid
import city.zouitel.tags.model.Tag
import city.zouitel.screens.navigation_drawer.NavigationDrawer
import city.zouitel.screens.navigation_drawer.Screens
import city.zouitel.screens.note_card.NoteCard
import city.zouitel.screens.top_action_bar.NoteTopAppBar
import city.zouitel.screens.top_action_bar.dialogs.EraseDialog
import city.zouitel.screens.top_action_bar.selection_bars.RemoveSelectionTopAppBar
import city.zouitel.systemDesign.Cons.JPEG
import city.zouitel.systemDesign.Cons.LIST
import city.zouitel.systemDesign.Cons.MP3
import city.zouitel.tags.viewmodel.TagScreenModel
import city.zouitel.tasks.viewmodel.NoteAndTaskScreenModel
import city.zouitel.tasks.viewmodel.TaskScreenModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File

class RemovedScreen : Screen, KoinComponent {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val dataModel: DataScreenModel by inject()
        val dataStoreVM: DataStoreVM by inject()
        val linkVM: LinkVM by inject()
        val noteAndLinkVM: NoteAndLinkVM by inject()

        val tagModel = getScreenModel<TagScreenModel>()
        val taskModel = getScreenModel<TaskScreenModel>()
        val noteAndTaskModel = getScreenModel<NoteAndTaskScreenModel>()
        val observerRemovedNotes = getScreenModel<HomeScreenModel>().allTrashedNotes.collectAsState()

        val context = LocalContext.current
        val searchTitleState = remember { mutableStateOf("") }

        val currentLayout = remember(dataStoreVM, dataStoreVM::getLayout).collectAsState()

        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scaffoldState = rememberScaffoldState()
        val topAppBarState = rememberTopAppBarState()
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
        val confirmationDialogState = remember { mutableStateOf(false) }

        val trashSelectionState = remember { mutableStateOf(false) }
        val selectedNotes = remember { mutableStateListOf<Data>() }

        //
        if (confirmationDialogState.value) {
            EraseDialog(dialogState = confirmationDialogState) {
                dataModel.eraseTrash()
                observerRemovedNotes.value.forEach { entity ->
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
                    tagModel = tagModel,
                    drawerState = drawerState,
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
                backgroundColor = MaterialTheme.colorScheme.surface,
                topBar = {
                    if (trashSelectionState.value) {
                        RemoveSelectionTopAppBar(
                            dataScreenModel = dataModel,
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
                            observerRemovedNotes.value.filter { entity ->
                                entity.dataEntity.title?.contains(searchTitleState.value, true)
                                    ?: true
                            }
                        ) { entity ->
                            NoteCard(
                                taskModel = taskModel,
                                noteAndTaskModel = noteAndTaskModel,
                                dataModel = dataModel,
                                screen = Screens.DELETED_SCREEN,
                                noteEntity = entity,
                                homeSelectionState = null,
                                trashSelectionState = trashSelectionState,
                                selectedNotes = selectedNotes
                            ) {
                                dataModel.deleteData(Data(uid = entity.dataEntity.uid))
                                entity.linkEntities.forEach { link ->
                                    linkVM.deleteLink(link)
                                }
                                entity.dataEntity.uid.let { _uid ->
                                    File(
                                        context.filesDir.path + File.pathSeparator + IMG_DIR,
                                        "$_uid.$JPEG"
                                    ).delete()
                                    File(
                                        context.filesDir.path + File.pathSeparator + REC_DIR,
                                        "$_uid$MP3"
                                    ).delete()
                                }
                            }
                        }
                    }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        item {
                            VerticalGrid(maxColumnWidth = 220.dp) {
                                observerRemovedNotes.value.filter { entity ->
                                    entity.dataEntity.title?.contains(searchTitleState.value, true)
                                        ?: true
                                }.forEach { entity ->
                                    NoteCard(
                                        taskModel = taskModel,
                                        noteAndTaskModel = noteAndTaskModel,
                                        dataModel = dataModel,
                                        screen = Screens.DELETED_SCREEN,
                                        noteEntity = entity,
                                        homeSelectionState = null,
                                        trashSelectionState = trashSelectionState,
                                        selectedNotes = selectedNotes
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
}
