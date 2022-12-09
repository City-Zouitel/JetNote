package com.example.jetnote.ui.camera_screen

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jetnote.icons.CAMERA_ICON
import com.example.jetnote.cons.CAMERA_ROUTE
import com.example.jetnote.icons.IMAGE_ICON
import com.example.jetnote.ui.AdaptingRow
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun AddImageDialog(
    dialogState: MutableState<Boolean>,
//    photoLaunch: ManagedActivityResultLauncher<Void?, Bitmap?>,
    imageLaunch: ManagedActivityResultLauncher<String, Uri?>,
    navController: NavController,
    uid: String?
) {

    AlertDialog(
        onDismissRequest = {
            dialogState.value = false
        },
        confirmButton = {},
        title = {
            Row {
                AdaptingRow(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Add Image", fontSize = 25.sp)
                }
            }
        },
        text = {
            Column {
                OutlinedIconButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    onClick = {
//                        prCamera.launchPermissionRequest()
//                        if (prCamera.status.isGranted) {
//                            photoLaunch.launch()
//                        } else {
//                            Toast
//                                .makeText(c, "permission denied", Toast.LENGTH_SHORT)
//                                .show()
//                        }
                        navController.navigate(
                            CAMERA_ROUTE + "/" +
                                    uid
                        )
                        dialogState.value = false
                    }) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp)
                    ) {
                        Icon(painterResource(id = CAMERA_ICON), contentDescription = null,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "Take Photo", fontSize = 17.sp)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedIconButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    onClick = {
                        imageLaunch.launch("image/*")
                        dialogState.value = false
                    }) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = IMAGE_ICON),
                            contentDescription = null,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "Choose Image", fontSize = 17.sp)
                    }
                }
            }
        },
    )
}