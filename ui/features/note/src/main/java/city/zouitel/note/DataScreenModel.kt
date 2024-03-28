package city.zouitel.note

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.DataUseCase
import city.zouitel.note.mapper.DataMapper
import city.zouitel.systemDesign.Cons.IMG_DIR
import city.zouitel.systemDesign.Cons.JPEG
import city.zouitel.note.model.Data as InData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.*

class DataScreenModel(
    private val add: DataUseCase.AddData,
    private val edit: DataUseCase.EditData,
    private val delete: DataUseCase.DeleteData,
    private val eraseTrash: DataUseCase.DeleteAllTrashedData,
    private val mapper: DataMapper,
    private val imgPath: String
): ScreenModel {

    var isProcessing by mutableStateOf(false)
        private set

    // for add a dataEntity from NoteEntityState as it to DataEntity class.
    fun addData(data: InData) {
        screenModelScope.launch(Dispatchers.IO) {
            isProcessing = true
            add.invoke(mapper.toDomain(data))
            isProcessing = false
        }
    }

    // for editData a dataEntity from NoteEntityState and put it to DataEntity class,
    // depending on changes.
    fun editData(data: InData){
        screenModelScope.launch(Dispatchers.IO) {
            isProcessing = true
            edit.invoke(mapper.toDomain(data))
            isProcessing = false
        }
    }

    // for deleting a dataEntity by the id.
    fun deleteData(data: InData){
        screenModelScope.launch(Dispatchers.IO) {
            isProcessing = true
            delete.invoke(mapper.toDomain(data))
            isProcessing = false
        }
    }

    //delete all trashed notes.
    fun eraseTrash() {
        screenModelScope.launch(Dispatchers.IO) {
            isProcessing = true
            eraseTrash.invoke()
            isProcessing = false
        }
    }

    fun saveImageLocally(img:Bitmap?, path:String, name:String?) {
            FileOutputStream(
                name?.let { File(imgPath, it) }
            ).use {
                img?.compress(Bitmap.CompressFormat.JPEG, 100, it)
                it.flush()
            }
    }

    fun decodeBitmapImage (img: MutableState<Bitmap?>?, photo: MutableState<Bitmap?>?, uri: Uri, c:Context){
        if (Build.VERSION.SDK_INT < 28) {
            runCatching {
                img?.value = MediaStore.Images.Media.getBitmap(c.contentResolver,uri)
            }
        } else {
            runCatching {
                val source = ImageDecoder.createSource(c.contentResolver,uri)
                img?.value = ImageDecoder.decodeBitmap(source)
            }
        }
        photo?.value?.let { img?.value = it }
    }

    fun imageDecoder(context: Context, uid:String): ImageBitmap? {
        val path = File(imgPath, "$uid.$JPEG")
        val bitImg = BitmapFactory.decodeFile(path.absolutePath)
        return bitImg?.asImageBitmap()
    }
}

