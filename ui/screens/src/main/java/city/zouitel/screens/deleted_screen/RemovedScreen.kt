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
import city.zouitel.audios.ui.AudioScreenModel
import city.zouitel.links.model.NoteAndLink
import city.zouitel.links.ui.LinkScreenModel
import city.zouitel.links.ui.NoteAndLinkScreenModel
import city.zouitel.screens.home_screen.HomeScreenModel
import city.zouitel.note.ui.DataScreenModel
import city.zouitel.note.model.Data
import city.zouitel.systemDesign.Cons.REC_DIR
import city.zouitel.systemDesign.Cons.IMG_DIR
import city.zouitel.systemDesign.Cons.SEARCH_IN_DELETED
import city.zouitel.systemDesign.DataStoreScreenModel
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
import java.io.File

class RemovedScreen : Screen, KoinComponent {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val context = LocalContext.current
//        val dataModel: DataScreenModel by inject()
//        val dataStoreVM: DataStoreScreenModel by inject()
//        val linkVM: LinkScreenModel by inject()
//        val noteAndLinkVM: NoteAndLinkScreenModel by inject()

        val dataModel = getScreenModel<DataScreenModel>()
        val tagModel = getScreenModel<TagScreenModel>()
        val taskModel = getScreenModel<TaskScreenModel>()
        val noteAndTaskModel = getScreenModel<NoteAndTaskScreenModel>()
        val audioModel = getScreenModel<AudioScreenModel>()
        val linkModel = getScreenModel<LinkScreenModel>()
        val noteAndLinkModel = getScreenModel<NoteAndLinkScreenModel>()
        val homeModel = getScreenModel<HomeScreenModel>()
        val dataStoreModel = getScreenModel<DataStoreScreenModel>()

        val searchTitleState = remember { mutableStateOf("") }
        val confirmationDialogState = remember { mutableStateOf(false) }
        val trashSelectionState = remember { mutableStateOf(false) }
        val selectedNotes = remember { mutableStateListOf<Data>() }

        val currentLayout = remember(dataStoreModel, dataStoreModel::getLayout).collectAsState()
        val observerRemovedNotes = remember(homeModel, homeModel::allTrashedNotes).collectAsState()

        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scaffoldState = rememberScaffoldState()
        val topAppBarState = rememberTopAppBarState()
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)

        //
        if (confirmationDialogState.value) {
            EraseDialog(dataStoreModel = dataStoreModel, dialogState = confirmationDialogState) {
                dataModel.eraseTrash()
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

        ModalNavigationDrawer(
            drawerContent = {
                NavigationDrawer(
                    dataStoreModel = dataStoreModel,
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
                            dataStoreModel = dataStoreModel,
                            dataModel = dataModel,
                            trashSelectionState = trashSelectionState,
                            selectedNotes = selectedNotes
                        )
                    } else {
                        NoteTopAppBar(
                            dataStoreModel = dataStoreModel,
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
                                dataStoreModel = dataStoreModel,
                                taskModel = taskModel,
                                noteAndTaskModel = noteAndTaskModel,
                                dataModel = dataModel,
                                audioModel = audioModel,
                                linkModel = linkModel,
                                noteAndLinkModel = noteAndLinkModel,
                                screen = Screens.DELETED_SCREEN,
                                noteEntity = entity,
                                homeSelectionState = null,
                                trashSelectionState = trashSelectionState,
                                selectedNotes = selectedNotes,
                                onSwipeNote = {
                                    dataModel.deleteData(Data(uid = entity.dataEntity.uid))
                                    entity.linkEntities.forEach { link ->
                                        linkModel.deleteLink(link)
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
                                },
                            )
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
                                        dataStoreModel = dataStoreModel,
                                        taskModel = taskModel,
                                        noteAndTaskModel = noteAndTaskModel,
                                        dataModel = dataModel,
                                        audioModel = audioModel,
                                        linkModel = linkModel,
                                        noteAndLinkModel = noteAndLinkModel,
                                        screen = Screens.DELETED_SCREEN,
                                        noteEntity = entity,
                                        homeSelectionState = null,
                                        trashSelectionState = trashSelectionState,
                                        selectedNotes = selectedNotes,
                                        onSwipeNote = {

                                        },
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
