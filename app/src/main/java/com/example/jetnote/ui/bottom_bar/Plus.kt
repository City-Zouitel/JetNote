package com.example.jetnote.ui.bottom_bar

import android.Manifest.permission
import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import com.example.jetnote.cons.*
import com.example.jetnote.db.entities.note.Note
import com.example.jetnote.icons.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("SuspiciousIndentation")
@Composable
internal fun Plus(
    isShow: MutableState<Boolean>,
    note: Note,
    navController: NavController,
    imageLaunch: ManagedActivityResultLauncher<String, Uri?>,
    recordDialogState: MutableState<Boolean>,
) {
    val permissionState = rememberMultiplePermissionsState(listOf(
        permission.RECORD_AUDIO,
        permission.WRITE_EXTERNAL_STORAGE,
    )) {
        recordDialogState.value = it.values.all { true }
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
            leadingIcon = {  Icon(painterResource(IMAGE_ICON),null) },
            onClick = {
                imageLaunch.launch("image/*")
                isShow.value = false
            }
        )
        DropdownMenuItem(
            text = { Text(text ="Take Photo", fontSize = 18.sp) },
            leadingIcon = { Icon( painterResource( CAMERA_ICON), null)},
            onClick = {
                navController.navigate(
                    CAMERA_ROUTE + "/" +
                            note.uid
                )
                isShow.value = false

            }
        )
        DropdownMenuItem(
            text = { Text(text = "Record", fontSize = 18.sp) },
            leadingIcon = { Icon(painterResource(MIC_ICON), null)},
            onClick = {
                permissionState.launchMultiplePermissionRequest()
                recordDialogState.value = permissionState.allPermissionsGranted
                isShow.value = false
            }
        )

        DropdownMenuItem(
            text = { Text(text = "Drawing", fontSize = 18.sp) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = GESTURE_ICON),
                    contentDescription = null
                )},
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
                isShow.value = false
            }
        )
        DropdownMenuItem(
            text = { Text(text = "Labels", fontSize = 18.sp) },
            leadingIcon = { Icon(painterResource(TAGS_ICON), null)},
            onClick = {
                navController.navigate("labels/${note.uid}")
                   isShow.value = false
            }
        )
        DropdownMenuItem(
            text = { Text(text = "Todo List", fontSize = 18.sp) },
            leadingIcon = { Icon(painterResource(LIST_CHECK_ICON), null)},
            onClick = {
                navController.navigate("todo/${note.uid}")
                isShow.value = false
            }
        )
    }
}