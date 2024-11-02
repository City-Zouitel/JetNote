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
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import city.zouitel.logic.events.UiEvent
import city.zouitel.note.model.Data
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
@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class,
)
@Composable
internal fun BottomBar(
    isNew: Boolean,
    id: String,
    titleState: TextFieldState?,
    descriptionState: TextFieldState?,
    dataStoreModel: DataStoreScreenModel,
    dataModel: DataScreenModel,
    imageLaunch: ManagedActivityResultLauncher<PickVisualMediaRequest, List<@JvmSuppressWildcards Uri>>,
    workspaceModel: WorkplaceScreenModel,
    priorityState: MutableState<String>,
    reminderState: MutableState<Long>
) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    val navigator = LocalNavigator.current
    val navBottomSheet = LocalBottomSheetNavigator.current

    val uiState by remember(workspaceModel, workspaceModel::uiState).collectAsState()
    val dateState by lazy { mutableStateOf(Calendar.getInstance().time) }

    val isMute = remember(dataStoreModel, dataStoreModel::isMute).collectAsState()
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
                                navBottomSheet.show(OptionsScreen(
                                    id = id,
                                    titleState = titleState,
                                    descriptionState = descriptionState,
                                    imageLaunch = imageLaunch,
                                    priorityState = priorityState,
                                    reminderState = reminderState
                                ))
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
                    painter = painterResource(id = if (isNew) CommonIcons.DONE_ICON else CommonIcons.EDIT_ICON),
                    contentDescription = "Add/Edit",
                    modifier = Modifier.clickable {
                        sound.performSoundEffect(context, CommonConstants.KEY_STANDARD, isMute.value)

                        if (isNew) {
                            dataModel.sendUiEvent(
                                UiEvent.Insert(
                                    Data(
                                        uid = id,
                                        title = titleState?.text.toString(),
                                        description = descriptionState?.text.toString(),
                                        priority = priorityState.value,
                                        reminding = reminderState.value,
                                        date = dateState.value.toString(),
                                        color = uiState.backgroundColor,
                                        textColor = uiState.textColor
                                    )
                                )
                            )
                        } else {
                            dataModel.sendUiEvent(
                                UiEvent.Update(
                                    Data(
                                        uid = id,
                                        title = titleState?.text.toString(),
                                        description = descriptionState?.text.toString(),
                                        priority = priorityState.value,
                                        reminding = reminderState.value,
                                        date = dateState.value.toString(),
                                        removed = 0,
                                        color = uiState.backgroundColor,
                                        textColor = uiState.textColor
                                    )
                                )
                            )
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