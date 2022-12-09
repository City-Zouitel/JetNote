package com.example.jetnote.ui.camera_screen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.jetnote.ui.camera_screen.Commons.REQUIRED_PERMISSIONS
import com.example.jetnote.vm.CameraXVM

@Composable
fun CameraCompose(
    context: Context,
    owner: LifecycleOwner,
    cameraX: CameraXVM,
    onCaptureClick: () -> Unit,
) {
    var hasCamPermission by remember {
        mutableStateOf(
            REQUIRED_PERMISSIONS.all {
                ContextCompat.checkSelfPermission(context, it) ==
                        PackageManager.PERMISSION_GRANTED
            })
    }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { granted ->
            hasCamPermission = granted.size == 2
        }
    )
    LaunchedEffect(key1 = true) {
        launcher.launch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        )
    }
    Column(modifier = Modifier.fillMaxSize()) {
        if (hasCamPermission) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { cameraX.startCameraPreviewView(context, owner) }
            )
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(), Arrangement.Bottom, Alignment.CenterHorizontally
    ) {
        Canvas(
            modifier = Modifier
                .size(70.dp)
                .padding(bottom = 3.dp)
                .clickable {
                    onCaptureClick.invoke()
                }
        ) {
            drawArc(
                color = Color.White,
                startAngle = 1f,
                sweepAngle = 360f,
                useCenter = true,
                style = Stroke(width = 10f,cap = StrokeCap.Round)
            )
        }
    }
}

