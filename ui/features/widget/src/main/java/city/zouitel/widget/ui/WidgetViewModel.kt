package city.zouitel.widget.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.WorkerThread
import androidx.lifecycle.ViewModel
import city.zouitel.domain.usecase.WidgetUseCase
import city.zouitel.systemDesign.Cons
import java.io.File

class WidgetViewModel /*@Inject*/ constructor(
    private val getAll: WidgetUseCase.GetAllWidgetMainEntityById
): ViewModel() {

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