package com.example.glance

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.WorkerThread
import com.example.common_ui.Cons
import com.example.domain.reposImpl.WidgetEntityRepoImpl
import java.io.File
import javax.inject.Inject

class WidgetVM @Inject constructor(
    private val widgetEntityRepoImpl: WidgetEntityRepoImpl
) {

    @WorkerThread
    fun getAllEntities() = widgetEntityRepoImpl.getAllWidgetEntityById

    @WorkerThread
    fun imageDecoder(context: Context, uid: String): Bitmap {
        val path = File(context.filesDir.path + "/" + Cons.IMAGES, "$uid.${Cons.JPEG}")
        val bitmapOptions = BitmapFactory.Options()
        bitmapOptions.inSampleSize = 4
        return BitmapFactory.decodeFile(path.absolutePath, bitmapOptions)
    }
}