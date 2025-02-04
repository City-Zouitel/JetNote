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
import city.zouitel.domain.utils.Action
import city.zouitel.note.ui.DataScreenModel
import city.zouitel.screens.utils.sound
import city.zouitel.systemDesign.CommonBottomSheet
import city.zouitel.systemDesign.CommonConstants.KEY_CLICK
import city.zouitel.systemDesign.CommonIcons.ERASER_ICON
import city.zouitel.systemDesign.CommonIcons.UNDO_ICON
import city.zouitel.systemDesign.CommonOptionItem
import city.zouitel.systemDesign.DataStoreScreenModel
import kotlinx.coroutines.launch

class OptionsScreen: Screen {
    @Composable
    override fun Content() {

        Options(
            dataStoreModel = getScreenModel(),
            dataModel = getScreenModel(),
            mainModel = getScreenModel()
        )
    }

    @Composable
    private fun Options(
        dataStoreModel: DataStoreScreenModel,
        dataModel: DataScreenModel,
        mainModel: MainScreenModel
    ) {
        val context = LocalContext.current
        val navBottomSheet = LocalBottomSheetNavigator.current

        val isMute by remember(dataStoreModel, dataStoreModel::isMute).collectAsState()
        val uiState by remember(mainModel, mainModel::uiState).collectAsState()
        val scope = rememberCoroutineScope()

        Navigator(CommonBottomSheet {
            Column {
                CommonOptionItem(
                    name = "Rollback",
                    icon = UNDO_ICON
                ) {
                    scope.launch {
                        sound.performSoundEffect(context, KEY_CLICK, isMute)
                        uiState.selectedNotes.map {
                            dataModel.sendAction(Action.Rollback(it.uid))
                        }
                        navBottomSheet.hide()
                    }
                }

                CommonOptionItem(
                    name = "Erase",
                    icon = ERASER_ICON
                ) {
                    sound.performSoundEffect(context, KEY_CLICK, isMute)
                    dataModel.sendAction(Action.DeleteByUid(uiState.selectedNote))
                    navBottomSheet.hide()
                }
            }
        })
    }
}