package com.example.jetnote.vm

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.jetnote.cons.IMAGE_FILE
import com.example.jetnote.cons.JPEG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.nio.ByteBuffer

class CameraXVM: ViewModel() {

    private var imageCapture: ImageCapture? = null

    fun startCameraPreviewView(context: Context, owner: LifecycleOwner,): PreviewView {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        val previewView = PreviewView(context)
        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }

        imageCapture = ImageCapture.Builder().build()

        val camSelector =
            CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
        kotlin.runCatching {
            cameraProviderFuture.get().bindToLifecycle(
                owner,
                camSelector,
                preview,
                imageCapture
            )
        } .onFailure {
            it.printStackTrace()
        }
        return previewView
    }

     fun capturePhoto(context: Context, owner: LifecycleOwner,uid: String?) = owner.lifecycleScope.launch{
        val imageCapture = imageCapture ?: return@launch

        imageCapture.takePicture(ContextCompat.getMainExecutor(context), object :
            ImageCapture.OnImageCapturedCallback(), ImageCapture.OnImageSavedCallback {
            override fun onCaptureSuccess(image: ImageProxy) {
                super.onCaptureSuccess(image)
                owner.lifecycleScope.launch {
                    saveMediaToStorage(
                        imageProxyToBitmap(image,owner),
                        uid!!,
                        context
                    )
                }
            }

            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
//                showLog("onCaptureSuccess: Uri  ${outputFileResults.savedUri}")
            }

            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)
//                showLog("onCaptureSuccess: onError")
            }
        })
    }

    private suspend fun imageProxyToBitmap(image: ImageProxy, owner: LifecycleOwner): Bitmap =
        withContext(owner.lifecycleScope.coroutineContext) {
            val planeProxy = image.planes[0]
            val buffer: ByteBuffer = planeProxy.buffer
            val bytes = ByteArray(buffer.remaining())
            buffer.get(bytes)
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }

    private suspend fun saveMediaToStorage(bitmap: Bitmap, name: String, context: Context,) {
        withContext(IO) {
            val filename = "$name.$JPEG"
            var fos: OutputStream? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                context.contentResolver?.also { resolver ->

                    val contentValues = ContentValues().apply {

                        put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                        put(MediaStore.MediaColumns.MIME_TYPE, "image/$JPEG")
                        put(
                            MediaStore.MediaColumns.RELATIVE_PATH,
                            Environment.DIRECTORY_DCIM
                        )
                    }
                    val imageUri: Uri? =
                        resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                    fos = imageUri?.let { with(resolver) { openOutputStream(it) } }
                }
            } else {
                val imagesDir =
//                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    context.filesDir.path + "/" + IMAGE_FILE + "/"
                val image = File(imagesDir, filename).also { fos = FileOutputStream(it) }
                Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
                    mediaScanIntent.data = Uri.fromFile(image)
                    context.sendBroadcast(mediaScanIntent)
                }
            }

            fos?.use {
                val success = async(IO) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                }
                if (success.await()) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Saved Successfully", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
}