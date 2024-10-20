package city.zouitel.screens.main_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import city.zouitel.links.model.NoteAndLink
import city.zouitel.links.ui.LinkScreenModel
import city.zouitel.links.ui.NoteAndLinkScreenModel
import city.zouitel.note.ui.DataScreenModel
import city.zouitel.systemDesign.CommonBottomSheet
import city.zouitel.systemDesign.CommonConstants
import city.zouitel.systemDesign.CommonConstants.KEY_CLICK
import city.zouitel.systemDesign.CommonIcons.ERASER_ICON
import city.zouitel.systemDesign.CommonIcons.UNDO_ICON
import city.zouitel.systemDesign.CommonOptionItem
import city.zouitel.systemDesign.DataStoreScreenModel
import city.zouitel.systemDesign.SoundEffect
import kotlinx.coroutines.launch

class OptionsScreen: Screen {
    @Composable
    override fun Content() {

        Options(
            dataStoreModel = getScreenModel(),
            dataModel = getScreenModel(),
            mainModel = getScreenModel(),
            linkModel = getScreenModel(),
            noteAndLinkModel = getScreenModel()
        )
    }

    @Composable
    private fun Options(
        dataStoreModel: DataStoreScreenModel,
        dataModel: DataScreenModel,
        mainModel: MainScreenModel,
        linkModel: LinkScreenModel,
        noteAndLinkModel: NoteAndLinkScreenModel,
    ) {
        val context = LocalContext.current
        val navBottomSheet = LocalBottomSheetNavigator.current

        val isMute by remember(dataStoreModel, dataStoreModel::isMute).collectAsState()
        val observerRemovedNotes = remember(mainModel, mainModel::allTrashedNotes).collectAsState()
        val uiState by remember(mainModel, mainModel::uiState).collectAsState()

        val scope = rememberCoroutineScope()
        val sound by lazy { SoundEffect() }

        Navigator(CommonBottomSheet({
            Column {
                CommonOptionItem(
                    name = "Rollback",
                    icon = UNDO_ICON
                ) {
                    scope.launch {
                        city.zouitel.screens.utils.sound.performSoundEffect(context, CommonConstants.KEY_CLICK, isMute)
                        uiState.selectedNote?.copy(removed = 0)?.let {
                            dataModel.editData(it)
                        }
                        navBottomSheet.hide()
                    }
                }

                CommonOptionItem(
                    name = "Erase",
                    icon = ERASER_ICON
                ) {
                    city.zouitel.screens.utils.sound.performSoundEffect(context, CommonConstants.KEY_CLICK, isMute)
                    uiState.selectedNote?.let { dataModel.deleteData(it) }

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
                    navBottomSheet.hide()
                }
            }
        }))
    }
}