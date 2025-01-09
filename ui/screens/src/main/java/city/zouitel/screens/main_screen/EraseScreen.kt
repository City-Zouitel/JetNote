package city.zouitel.screens.main_screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import city.zouitel.links.ui.LinkScreenModel
import city.zouitel.logic.events.UiEvent
import city.zouitel.note.ui.DataScreenModel
import city.zouitel.screens.utils.sound
import city.zouitel.systemDesign.CommonBottomSheet
import city.zouitel.systemDesign.CommonConstants
import city.zouitel.systemDesign.CommonOptionItem
import city.zouitel.systemDesign.DataStoreScreenModel
import kotlinx.coroutines.launch

data class EraseScreen(val onConfirm: () -> Unit): Screen {
    @Composable
    override fun Content() {

        Erase(
            dataStoreModel = getScreenModel(),
            dataModel = getScreenModel(),
            mainModel = getScreenModel(),
            linkModel = getScreenModel()
        )
    }

    @Composable
    private fun Erase(
        dataStoreModel: DataStoreScreenModel,
        dataModel: DataScreenModel,
        mainModel: MainScreenModel,
        linkModel: LinkScreenModel,
    ) {
        val context = LocalContext.current
        val navBottom = LocalBottomSheetNavigator.current

        val thereIsSoundEffect = remember(dataStoreModel, dataStoreModel::isMute).collectAsState()
        val observerRemovedNotes = remember(mainModel, mainModel::allTrashedNotes).collectAsState()
        val scope = rememberCoroutineScope()

        Navigator(CommonBottomSheet {

            LazyColumn {
                item {
                    Text(
                        modifier = Modifier.padding(7.dp),
                        text = "Empty Notes?",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        modifier = Modifier.padding(7.dp),
                        text = "All notes in trash will be permanently deleted.",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    CommonOptionItem(
                        onConfirm = {
                            scope.launch {
                                sound.performSoundEffect(
                                    context,
                                    CommonConstants.KEY_CLICK,
                                    thereIsSoundEffect.value
                                )
                                dataModel.sendUiEvent(UiEvent.DeleteAll())
                                observerRemovedNotes.value.forEach { entity ->
                                    entity.tagEntities.forEach {

                                    }
                                }
                            }.invokeOnCompletion {
                                navBottom.hide()
                            }

                        },
                        onDismiss = {
                            sound.performSoundEffect(
                                context,
                                CommonConstants.KEY_CLICK,
                                thereIsSoundEffect.value
                            )
                            navBottom.hide()
                        }
                    )
                }
            }
        })
    }
}
