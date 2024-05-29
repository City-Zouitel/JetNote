package city.zouitel.note.ui.bottom_bar

import android.Manifest
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import city.zouitel.logic.asShortToast
import city.zouitel.note.utils.ColorsRow
import city.zouitel.note.ui.workplace.WorkplaceScreenModel
import city.zouitel.systemDesign.CommonRow
import city.zouitel.systemDesign.Cons.FOCUS_NAVIGATION
import city.zouitel.systemDesign.Cons.KEY_CLICK
import city.zouitel.systemDesign.DataStoreScreenModel
import city.zouitel.systemDesign.Icons.ADD_CIRCLE_ICON
import city.zouitel.systemDesign.Icons.BELL_ICON
import city.zouitel.systemDesign.Icons.BELL_RING_ICON_24
import city.zouitel.systemDesign.CommonPopupTip
import city.zouitel.systemDesign.RationalDialog
import city.zouitel.systemDesign.SoundEffect
import city.zouitel.systemDesign.listOfBackgroundColors
import city.zouitel.systemDesign.listOfTextColors
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class,
    ExperimentalPermissionsApi::class
)
@Composable
fun AddEditBottomBar(
    dataStoreModel: DataStoreScreenModel,
    id: String,
//    navController: NavController,
    imageLaunch: ManagedActivityResultLauncher<String, Uri?>,
//    recordDialogState: MutableState<Boolean>,
    workspaceModel: WorkplaceScreenModel,
//    remindingDialogState: MutableState<Boolean>,
//    backgroundColorState: MutableState<Int>,
//    textColorState: MutableState<Int>,
//    priorityColorState: MutableState<String>,
//    remindingValue: MutableLongState,
    titleState: TextFieldState?,
    descriptionState: TextFieldState?,
) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    val navigator = LocalNavigator.current

    val showOptionsMenu = remember { mutableStateOf(false) }
    val thereIsSoundEffect = remember(dataStoreModel, dataStoreModel::getSound).collectAsState()

    val sound = SoundEffect()

    val formatter = SimpleDateFormat("dd-MM-yyyy hh:mm")

    val permissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberMultiplePermissionsState(
            permissions = listOf(
                Manifest.permission.POST_NOTIFICATIONS,
            )
        ) {
            context.run {
//                "Notification permission granted.".asShortToast()
                workspaceModel.updateRecorderDialog(true)
            }
        }
    } else {
        rememberMultiplePermissionsState(
            permissions = listOf()
        ) {
//            recordDialogState.value = true
            workspaceModel.updateRecorderDialog(true)
//            navigator?.push()
        }
    }
    val showRationalDialog = remember { mutableStateOf(false) }

    RationalDialog(
        showRationalDialog = showRationalDialog,
        permissionState = permissionState,
        permissionName = "post notification"
    )

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
                                showOptionsMenu.value = !showOptionsMenu.value
                                sound.makeSound.invoke(
                                    context,
                                    FOCUS_NAVIGATION,
                                    thereIsSoundEffect.value
                                )
                            }
                    )
                }

                CommonPopupTip(
                    message = if (workspaceModel.uiState.reminding != 0L) {
                        formatter.format(workspaceModel.uiState.reminding)
                    } else {
                        "Reminding"
                    }
                ) {
                    Icon(
                        painter = painterResource(
                            if (workspaceModel.uiState.reminding != 0L) BELL_RING_ICON_24 else BELL_ICON
                        ),
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
                                sound.makeSound.invoke(context, KEY_CLICK, thereIsSoundEffect.value)
                                if (!permissionState.allPermissionsGranted) {
                                    if (permissionState.shouldShowRationale) {
                                        showRationalDialog.value = true
                                    } else {
                                        permissionState.launchMultiplePermissionRequest()
                                    }
                                } else {
//                                    remindingDialogState.value = true
//                                    navigator?.push()
                                    workspaceModel.updateRemindingDialog(true)
                                }
                            }
                    )
                }

                // undo
                UndoRedo(
                    dataStoreModel = dataStoreModel,
                    workplaceModel = workspaceModel,
                    titleState = titleState,
                    descriptionState = descriptionState,
                )
            }
        }

        // more options menu.
        Options(
            dataStoreModel = dataStoreModel,
            isShow = showOptionsMenu,
            id = id,
//            navController = navController,
            imageLaunch = imageLaunch,
//            recordDialogState = recordDialogState,
            workspaceModel = workspaceModel,
//            priorityColorState = priorityColorState
        )

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





