package com.example.jetnote.ui.camera_screen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

object Commons {
    val REQUIRED_PERMISSIONS =
        mutableListOf(
            Manifest.permission.CAMERA,
        ).apply {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()

    fun allPermissionsGranted(ctx: Context) =
        REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(ctx, it) ==
                    PackageManager.PERMISSION_GRANTED
        }
}