package city.zouitel.note.ui.bottom_bar

import android.Manifest.permission
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.internal.illegalDecoyCallException
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import city.zouitel.note.model.Data
import city.zouitel.systemDesign.Cons.DRAW_ROUTE
import city.zouitel.systemDesign.Cons.KEY_CLICK
import city.zouitel.systemDesign.Cons.KEY_STANDARD
import city.zouitel.systemDesign.Cons.TAG_ROUTE
import city.zouitel.systemDesign.Cons.TASK_ROUTE
import city.zouitel.systemDesign.DataStoreVM
import city.zouitel.systemDesign.Icons.CAMERA_ICON
import city.zouitel.systemDesign.Icons.GESTURE_ICON
import city.zouitel.systemDesign.Icons.IMAGE_ICON
import city.zouitel.systemDesign.Icons.INBOX
import city.zouitel.systemDesign.Icons.LIST_CHECK_ICON
import city.zouitel.systemDesign.Icons.MIC_ICON
import city.zouitel.systemDesign.Icons.TAGS_ICON
import city.zouitel.systemDesign.SoundEffect
import city.zouitel.systemDesign.getColorOfPriority
import city.zouitel.systemDesign.getPriorityOfColor
import city.zouitel.systemDesign.listOfPriorityColors
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("SuspiciousIndentation")
@Composable
internal fun Plus(
    dataStoreVM: DataStoreVM = koinViewModel(),
    isShow: MutableState<Boolean>,
    note: Data,
    navController: NavController,
    imageLaunch: ManagedActivityResultLauncher<String, Uri?>,
    recordDialogState: MutableState<Boolean>,
    priorityColorState: MutableState<String>,
) {
    val context = LocalContext.current
    val thereIsSoundEffect = remember(dataStoreVM, dataStoreVM::getSound).collectAsState()

    val sound by lazy { SoundEffect() }

    val permissionState = rememberMultiplePermissionsState(
        permissions =  listOf(
            permission.RECORD_AUDIO,
            /*permission.WRITE_EXTERNAL_STORAGE*/
        )
    ) {
        if (it.getValue(permission.RECORD_AUDIO)) {
            recordDialogState.value = true
        }
    }
    val currentColor = remember { mutableStateOf(getColorOfPriority(priorityColorState.value)) }

    val showRationalDialog = remember { mutableStateOf(false) }
    if (showRationalDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showRationalDialog.value = false
            },
            title = {
                Text(
                    text = "Permission",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            },
            text = {
                Text(
                    if (permissionState.revokedPermissions.size == 1) {
                        "You need audio record and wright external storage permission to contourne"
                    } else if (permissionState.revokedPermissions.first().permission == permission.RECORD_AUDIO) {
                        "You need audio record permission. Please grant the permission."
                    } else {
                        throw Exception("...")
                    },
                    fontSize = 16.sp
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showRationalDialog.value = false
                        val intent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", context.packageName, null)
                        )
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        ContextCompat.startActivity(context, intent, null)
                    }) {
                    Text(
                        text = "Ok",
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showRationalDialog.value = false
                    }) {
                    Text(
                        text = "Cancel",
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                }
            },
        )
    }

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
            leadingIcon = { Icon(painterResource(IMAGE_ICON), null) },
            onClick = {
                sound.makeSound(context, KEY_CLICK, thereIsSoundEffect.value)
                imageLaunch.launch("image/*")
                isShow.value = false
            }
        )
        DropdownMenuItem(
            text = { Text(text = "Add Audio", fontSize = 18.sp) },
            leadingIcon = {  },
            onClick = {
                sound.makeSound(context, KEY_CLICK, thereIsSoundEffect.value)
                // TODO: ...
                isShow.value = false
            },
            enabled = false
        )
        DropdownMenuItem(
            text = { Text(text = "Take Photo", fontSize = 18.sp) },
            leadingIcon = { Icon(painterResource(CAMERA_ICON), null) },
            onClick = {
//                navController.navigate(
//                    CAMERA_ROUTE + "/" +
//                            dataEntity.uid
//                )
//                isShow.value = false
                Toast.makeText(context, "Coming Soon.", Toast.LENGTH_SHORT).show()
            },
            enabled = false
        )
        DropdownMenuItem(
            text = { Text(text = "Record", fontSize = 18.sp) },
            leadingIcon = { Icon(painterResource(MIC_ICON), null) },
            onClick = {
                sound.makeSound(context, KEY_CLICK, thereIsSoundEffect.value)
                if(!permissionState.allPermissionsGranted) {
                    if (permissionState.shouldShowRationale) {
                        showRationalDialog.value = true
                    } else {
                        permissionState.launchMultiplePermissionRequest()
                    }
                } else {
                    recordDialogState.value = true
                }
                isShow.value = false
            }
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
                navController.navigate(
                    route = DRAW_ROUTE + "/" +
                            note.title + "/" +
                            note.description + "/" +
                            note.color + "/" +
                            note.textColor + "/" +
                            note.priority + "/" +
                            note.uid + "/" +
                            note.audioDuration + "/" +
                            note.reminding
                )
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
                with(navController) {
                    navigate("$TAG_ROUTE/${note.uid}")
                    clearBackStack("$TAG_ROUTE/${note.uid}")
                }
            }
        )
        DropdownMenuItem(
            text = { Text(text = "Task List", fontSize = 18.sp) },
            leadingIcon = { Icon(painterResource(LIST_CHECK_ICON), null) },
            onClick = {
                sound.makeSound(context, KEY_CLICK, thereIsSoundEffect.value)
                isShow.value = false
                with(navController) {
                    navigate("$TASK_ROUTE/${note.uid}")
                    clearBackStack("$TASK_ROUTE/${note.uid}")
                }
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
                                    currentColor.value = it
                                    priorityColorState.value = getPriorityOfColor(it)
                                    sound.makeSound.invoke(
                                        context,
                                        KEY_CLICK,
                                        thereIsSoundEffect.value
                                    )
                                }
                        ) {
                            drawArc(
                                color = if (it == Color.Transparent) Color.Gray else it,
                                startAngle = 1f,
                                sweepAngle = 360f,
                                useCenter = true,
                                style =
                                if (currentColor.value == it) {
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

