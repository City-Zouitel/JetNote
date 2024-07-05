package city.zouitel.note.ui.bottom_bar

import android.Manifest.permission
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import city.zouitel.audios.ui.list.AudioListScreen
import city.zouitel.logic.getColorOfPriority
import city.zouitel.logic.getPriorityOfColor
import city.zouitel.note.ui.workplace.WorkplaceScreenModel
import city.zouitel.systemDesign.CommonConstants.KEY_CLICK
import city.zouitel.systemDesign.CommonConstants.KEY_STANDARD
import city.zouitel.systemDesign.DataStoreScreenModel
import city.zouitel.systemDesign.CommonIcons.ADD_IMAGE_ICON
import city.zouitel.systemDesign.CommonIcons.CAMERA_ICON
import city.zouitel.systemDesign.CommonIcons.CASSETTE_ICON
import city.zouitel.systemDesign.CommonIcons.GESTURE_ICON
import city.zouitel.systemDesign.CommonIcons.LIST_CHECK_ICON
import city.zouitel.systemDesign.CommonIcons.MIC_ICON
import city.zouitel.systemDesign.CommonIcons.TAGS_ICON
import city.zouitel.systemDesign.RationalDialog
import city.zouitel.systemDesign.SoundEffect
import city.zouitel.systemDesign.listOfPriorityColors
import city.zouitel.tags.ui.TagsScreen
import city.zouitel.tasks.ui.TasksScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("SuspiciousIndentation", "UnrememberedMutableState")
@Composable
internal fun Options(
    dataStoreModel: DataStoreScreenModel,
    isShow: MutableState<Boolean>,
    id: String,
    imageLaunch: ManagedActivityResultLauncher<PickVisualMediaRequest, List<@JvmSuppressWildcards Uri>>,
    workspaceModel: WorkplaceScreenModel,
) {
    val context = LocalContext.current
    val navigator = LocalNavigator.currentOrThrow
    val navBottomSheet = LocalBottomSheetNavigator.current

    val thereIsSoundEffect = remember(dataStoreModel, dataStoreModel::getSound).collectAsState()
    val uiState by remember(workspaceModel, workspaceModel::uiState).collectAsState()

    val sound by lazy { SoundEffect() }

    val recorderPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberMultiplePermissionsState(
            permissions =  listOf(
                permission.RECORD_AUDIO,
                permission.READ_MEDIA_AUDIO
            )
        ) {
            if (it.all { true }) {
                workspaceModel.updateRecorderDialog(true)
            }
        }
    } else {
        rememberMultiplePermissionsState(
            permissions =  listOf(
                permission.RECORD_AUDIO,
            )
        ) {
            if (it.all { true }) {
                workspaceModel.updateRecorderDialog(true)

            }
        }
    }

    val readMediaPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberMultiplePermissionsState(
            permissions =  listOf(
                permission.READ_MEDIA_AUDIO
            )
        ) {
            if (it.all { true }) {
                navBottomSheet.show(AudioListScreen(id))
            }
        }
    } else {
        rememberMultiplePermissionsState(
            permissions =  emptyList()
        ) {
            navBottomSheet.show(AudioListScreen(id))
        }
    }

    val recorderRationalDialog = remember { mutableStateOf(false) }
    val readMediaRationalDialog = remember { mutableStateOf(false) }

    RationalDialog(
        showRationalDialog = recorderRationalDialog,
        permissionState = recorderPermissions,
        permissionName = "audio record"
    )

    RationalDialog(
        showRationalDialog = readMediaRationalDialog,
        permissionState = readMediaPermissions,
        permissionName = "audio record"
    )

    DropdownMenu(
        expanded = isShow.value,
        onDismissRequest = {
            isShow.value = false
        },
        properties = PopupProperties(
            focusable = true
        )
    ) {
        DropdownMenuItem(
            text = { Text(text = "Add Image", fontSize = 18.sp) },
            leadingIcon = { Icon(painterResource(ADD_IMAGE_ICON), null) },
            onClick = {
                sound.makeSound(context, KEY_CLICK, thereIsSoundEffect.value)
                imageLaunch.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                isShow.value = false
            },
        )
        DropdownMenuItem(
            text = { Text(text = "Add Audio", fontSize = 18.sp) },
            leadingIcon = { Icon(painterResource(CASSETTE_ICON), null) },
            onClick = {
                sound.makeSound(context, KEY_CLICK, thereIsSoundEffect.value)
                if(!readMediaPermissions.allPermissionsGranted) {
                    if (readMediaPermissions.shouldShowRationale) {
                        readMediaRationalDialog.value = true
                    } else {
                        readMediaPermissions.launchMultiplePermissionRequest()
                    }
                } else {
                    navBottomSheet.show(AudioListScreen(id))
                }
                isShow.value = false
            },
            enabled = true
        )
        DropdownMenuItem(
            text = { Text(text = "Take Photo", fontSize = 18.sp) },
            leadingIcon = { Icon(painterResource(CAMERA_ICON), null) },
            onClick = {
                Toast.makeText(context, "Coming Soon.", Toast.LENGTH_SHORT).show()
            },
            enabled = false
        )
        DropdownMenuItem(
            text = { Text(text = "Record", fontSize = 18.sp) },
            leadingIcon = { Icon(painterResource(MIC_ICON), null) },
            onClick = {
                sound.makeSound(context, KEY_CLICK, thereIsSoundEffect.value)
                if (!recorderPermissions.allPermissionsGranted) {
                    if (recorderPermissions.shouldShowRationale) {
                        recorderRationalDialog.value = true
                    } else {
                        recorderPermissions.launchMultiplePermissionRequest()
                    }
                } else {
                    workspaceModel.updateRecorderDialog(true)
                }
                isShow.value = false
            },
            enabled = false
        )

        DropdownMenuItem(
            text = { Text(text = "Drawing", fontSize = 18.sp) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = GESTURE_ICON),
                    contentDescription = null
                )
            },
            onClick = {
//                navController.navigate(
//                    route = DRAW_ROUTE + "/" +
//                            note.title + "/" +
//                            note.description + "/" +
//                            note.backgroundColor + "/" +
//                            note.textColor + "/" +
//                            note.priority + "/" +
//                            note.uid + "/" +
//                            note.audioDuration + "/" +
//                            note.reminding
//                )
                sound.makeSound(context, KEY_CLICK, thereIsSoundEffect.value)
                isShow.value = false
            },
            enabled = false
        )
        DropdownMenuItem(
            text = { Text(text = "Tags", fontSize = 18.sp) },
            leadingIcon = { Icon(painterResource(TAGS_ICON), null) },
            onClick = {
                sound.makeSound(context, KEY_CLICK, thereIsSoundEffect.value)
                isShow.value = false
                navigator.push(TagsScreen(id = id))
            }
        )
        DropdownMenuItem(
            text = { Text(text = "Task List", fontSize = 18.sp) },
            leadingIcon = { Icon(painterResource(LIST_CHECK_ICON), null) },
            onClick = {
                sound.makeSound(context, KEY_CLICK, thereIsSoundEffect.value)
                isShow.value = false
                navigator.push(TasksScreen(id = id))
            }
        )
        DropdownMenuItem(
            text = {
                Row {
                    listOfPriorityColors.forEach {
                        Spacer(modifier = Modifier.width(3.dp))
                        Canvas(
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    sound.makeSound.invoke(
                                        context,
                                        KEY_CLICK,
                                        thereIsSoundEffect.value
                                    )
                                    workspaceModel.updatePriority(getPriorityOfColor(it.toArgb()))
                                }
                        ) {
                            drawArc(
                                color = if (it == Color.Transparent) Color.Gray else it,
                                startAngle = 1f,
                                sweepAngle = 360f,
                                useCenter = true,
                                style =
                                if (getColorOfPriority(uiState.priority) == it.toArgb()) {
                                    Stroke(width = 5f, cap = StrokeCap.Round)
                                } else {
                                    Fill
                                }
                            )
                        }
                        Spacer(modifier = Modifier.width(3.dp))
                    }
                }
            },
            onClick = {
                sound.makeSound(context, KEY_STANDARD, thereIsSoundEffect.value)
            }
        )
    }
}

