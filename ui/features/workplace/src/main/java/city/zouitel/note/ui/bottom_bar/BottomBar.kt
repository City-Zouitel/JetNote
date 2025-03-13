package city.zouitel.note.ui.bottom_bar

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import city.zouitel.domain.utils.Action
import city.zouitel.links.ui.LinkScreenModel
import city.zouitel.links.ui.cacheLink
import city.zouitel.note.model.Data
import city.zouitel.note.providers.Options
import city.zouitel.note.ui.DataScreenModel
import city.zouitel.note.ui.utils.ColorsRow
import city.zouitel.note.ui.utils.listOfTextColors
import city.zouitel.note.ui.workplace.WorkplaceScreenModel
import city.zouitel.systemDesign.CommonConstants
import city.zouitel.systemDesign.CommonConstants.FOCUS_NAVIGATION
import city.zouitel.systemDesign.CommonIcons
import city.zouitel.systemDesign.CommonIcons.ADD_CIRCLE_ICON
import city.zouitel.systemDesign.CommonPopupTip
import city.zouitel.systemDesign.CommonRow
import city.zouitel.systemDesign.DataStoreScreenModel
import city.zouitel.systemDesign.SoundEffect
import city.zouitel.systemDesign.listOfBackgroundColors

@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class)
@Composable
internal fun BottomBar(
    isNew: Boolean,
    uid: String,
    titleState: TextFieldState?,
    descriptionState: TextFieldState?,
    dataStoreModel: DataStoreScreenModel,
    dataModel: DataScreenModel,
    linkModel: LinkScreenModel,
    workspaceModel: WorkplaceScreenModel,
    priorityState: MutableState<Int>
) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    val navigator = LocalNavigator.current
    val navBottomSheet = LocalBottomSheetNavigator.current

    val uiState by remember(workspaceModel, workspaceModel::uiState).collectAsState()
    val isMute = remember(dataStoreModel, dataStoreModel::isMute).collectAsState()
    val optionsScreen = rememberScreen(Options(
        uid = uid,
        titleState = titleState,
        descriptionState = descriptionState,
        priorityState = priorityState
    ))

    val dateState by lazy { mutableStateOf(Calendar.getInstance().time) }
    val sound by lazy { SoundEffect() }

    Column {
        Row {
            CommonRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .height(50.dp)
            ) {
                CommonPopupTip(message = "More Options") {
                    Icon(
                        painter = painterResource(id = ADD_CIRCLE_ICON),
                        contentDescription = null,
                        tint = contentColorFor(backgroundColor = MaterialTheme.colorScheme.surfaceVariant),
                        modifier = Modifier
                            .combinedClickable(
                                onLongClick = {
                                    // To make vibration.
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    it.showAlignTop()
                                }
                            ) {
                                sound.performSoundEffect(context, FOCUS_NAVIGATION, isMute.value)
                                navBottomSheet.show(optionsScreen)
                            }
                    )
                }

                // undo
                UndoRedo(
                    dataStoreModel = dataStoreModel,
                    workspaceModel = workspaceModel,
                    titleState = titleState,
                    descriptionState = descriptionState,
                )

                Icon(
                    painter = painterResource(CommonIcons.DONE_ICON),
                    contentDescription = "add a note",
                    modifier = Modifier.clickable {
                        sound.performSoundEffect(context, CommonConstants.KEY_STANDARD, isMute.value)
                            dataModel.sendAction(
                                Action.Insert(
                                    Data(
                                        uid = uid,
                                        title = titleState?.text.toString(),
                                        description = descriptionState?.text.toString(),
                                        priority = priorityState.value,
                                        date = dateState.value.toString(),
                                        background = uiState.backgroundColor,
                                        textColor = uiState.textColor
                                    )
                                )
                            )
                        linkModel.findUrlLink(descriptionState?.text?.toString())?.map {
                            cacheLink(it, uid)
                        }
                        navigator?.pop()
                    }
                )
            }
        }

        // row of background colors.
        ColorsRow(
            dataStoreModel = dataStoreModel,
            colors = listOfBackgroundColors
        ) { color ->
            workspaceModel.updateBackgroundColor(color)
        }

        // row of text colors.
        ColorsRow(
            dataStoreModel = dataStoreModel,
            colors = listOfTextColors
        ) { color ->
            workspaceModel.updateTextColor(color)
        }
    }
}