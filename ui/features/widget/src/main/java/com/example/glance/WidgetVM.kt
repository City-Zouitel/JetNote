package com.example.glance

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.WorkerThread
import com.example.common_ui.Cons
import com.example.domain.usecase.WidgetUseCase
import java.io.File
import javax.inject.Inject

class WidgetVM @Inject constructor(
    private val getAll: WidgetUseCase.GetAllWidgetMainEntityById
) {

    @WorkerThread
    fun getAllEntities() = getAll

    @WorkerThread
    fun imageDecoder(context: Context, uid: String): Bitmap {
        val path = File(context.filesDir.path + "/" + Cons.IMAGES, "$uid.${Cons.JPEG}")
        val bitmapOptions = BitmapFactory.Options()
        bitmapOptions.inSampleSize = 4
        return BitmapFactory.decodeFile(path.absolutePath, bitmapOptions)
    }
}