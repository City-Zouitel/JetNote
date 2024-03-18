package city.zouitel.widget.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.WorkerThread
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.WidgetUseCase
import city.zouitel.systemDesign.Cons
import city.zouitel.widget.mapper.WidgetMapper
import city.zouitel.widget.model.WidgetNote as InNote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File

class WidgetScreenModel(
    private val getAllNotes: WidgetUseCase.GetAllWidgetMainEntityById,
    private val mapper: WidgetMapper
): ScreenModel {

    // for observing the dataEntity changes.
    private val _allNotesById = MutableStateFlow<List<InNote>>(emptyList())
    val allNotesById: StateFlow<List<InNote>>
        get() = _allNotesById.stateIn(screenModelScope, SharingStarted.WhileSubscribed(), listOf())

    init {
        screenModelScope.launch(context = Dispatchers.IO) {
            getAllNotes.invoke().collect { list ->
                _allNotesById.value = list.map { note -> mapper.toView(note) }
            }
        }
    }

    @WorkerThread
    fun imageDecoder(context: Context, uid: String): Bitmap {
        val path = File(context.filesDir.path + "/" + Cons.IMG_DIR, "$uid.${Cons.JPEG}")
        val bitmapOptions = BitmapFactory.Options()
        bitmapOptions.inSampleSize = 4
        return BitmapFactory.decodeFile(path.absolutePath, bitmapOptions)
    }
}