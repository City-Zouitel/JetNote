package city.zouitel.note.ui.bottom_bar

import android.Manifest
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.widget.Toast
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
import androidx.navigation.NavController
import cafe.adriel.voyager.navigator.LocalNavigator
import city.zouitel.note.ui.ColorsRow
import city.zouitel.note.model.Data
import city.zouitel.recoder.ui.RecorderScreen
import city.zouitel.systemDesign.CommonRow
import city.zouitel.systemDesign.Cons.FOCUS_NAVIGATION
import city.zouitel.systemDesign.Cons.KEY_CLICK
import city.zouitel.systemDesign.DataStoreVM
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
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class,
    ExperimentalPermissionsApi::class
)
@Composable
fun AddEditBottomBar(
    dataStoreVM: DataStoreVM = koinViewModel(),
    note: Data,
    navController: NavController,
    imageLaunch: ManagedActivityResultLauncher<String, Uri?>,
    recordDialogState: MutableState<Boolean>,
    remindingDialogState: MutableState<Boolean>,
    backgroundColorState: MutableState<Int>,
    textColorState: MutableState<Int>,
    priorityColorState: MutableState<String>,
    remindingValue: MutableLongState,
    titleState: Pair<TextFieldState?, Boolean>,
    descriptionState: TextFieldState?,
) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    val navigator = LocalNavigator.current

    val showOptionsMenu = remember { mutableStateOf(false) }
    val thereIsSoundEffect = remember(dataStoreVM, dataStoreVM::getSound).collectAsState()

    val sound = SoundEffect()

    val formatter = SimpleDateFormat("dd-MM-yyyy hh:mm")

    val permissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberMultiplePermissionsState(
            permissions = listOf(
                Manifest.permission.POST_NOTIFICATIONS,
            )
        ) {
            Toast.makeText(context, "Notification permission granted.", Toast.LENGTH_SHORT).show()
        }
    } else {
        rememberMultiplePermissionsState(
            permissions = listOf()
        ) {
//            recordDialogState.value = true
            navigator?.push(RecorderScreen(note.uid))
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
                    message = if (remindingValue.longValue != 0L) {
                        formatter.format(remindingValue.longValue)
                    } else {
                        "Reminding"
                    }
                ) {
                    Icon(
                        painter = painterResource(
                            if (remindingValue.longValue != 0L) BELL_RING_ICON_24 else BELL_ICON
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
                                    navigator?.push(RecorderScreen(note.uid))
                                }
                            }
                    )
                }

                // undo
                UndoRedo(
                    titleState = Pair(titleState.first, titleState.second),
                    descriptionState = descriptionState,
                )
            }
        }

        // more options menu.
        Plus(
            isShow = showOptionsMenu,
            note = note,
            navController = navController,
            imageLaunch = imageLaunch,
            recordDialogState = recordDialogState,
            priorityColorState = priorityColorState
        )

        // row of background colors.
        ColorsRow(
            colorState = backgroundColorState,
            colors = listOfBackgroundColors
        )

        // row of text colors.
        ColorsRow(
            colorState = textColorState,
            colors = listOfTextColors
        )
    }
}





