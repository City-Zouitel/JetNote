package city.zouitel.note.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.DataUseCase
import city.zouitel.note.mapper.DataMapper
import city.zouitel.systemDesign.CommonConstants.JPEG
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

    // for add a dataEntity from NoteEntityState as it to DataEntity class.
    fun addData(data: InData) {
        screenModelScope.launch(Dispatchers.IO) {
            add.invoke(mapper.toDomain(data))
        }
    }

    // for editData a dataEntity from NoteEntityState and put it to DataEntity class,
    // depending on changes.
    fun editData(data: InData){
        screenModelScope.launch(Dispatchers.IO) {
            edit.invoke(mapper.toDomain(data))
        }
    }

    // for deleting a dataEntity by the id.
    fun deleteData(data: InData){
        screenModelScope.launch(Dispatchers.IO) {
            delete.invoke(mapper.toDomain(data))
        }
    }

    //delete all removed notes.
    fun eraseNotes() {
        screenModelScope.launch(Dispatchers.IO) {
            eraseTrash.invoke()
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
}